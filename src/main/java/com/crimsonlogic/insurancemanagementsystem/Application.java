package com.crimsonlogic.insurancemanagementsystem;

import com.crimsonlogic.insurancemanagementsystem.config.MyBatisUtil;
import com.crimsonlogic.insurancemanagementsystem.enums.Role;
import com.crimsonlogic.insurancemanagementsystem.menu.AdminMenu;
import com.crimsonlogic.insurancemanagementsystem.menu.AgentMenu;
import com.crimsonlogic.insurancemanagementsystem.menu.CustomerMenu;
import com.crimsonlogic.insurancemanagementsystem.menu.EmployeeMenu;
import com.crimsonlogic.insurancemanagementsystem.model.User;
import com.crimsonlogic.insurancemanagementsystem.service.AuthService;
import com.crimsonlogic.insurancemanagementsystem.service.impl.AuthServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;

public class Application {

    private final Scanner scanner = new Scanner(System.in);

    private final AuthService authService = new AuthServiceImpl();

    /*
     * Maps each role to its corresponding menu.
     *
     * Function<User, Runnable> is used because the logged-in user
     * must be passed to the appropriate menu.
     */
    private final Map<Role, Function<User, Runnable>> roleMenus =
            new HashMap<>();

    public Application() {

        roleMenus.put(
                Role.ADMIN,
                user -> () -> new AdminMenu(scanner, user).show()
        );

        roleMenus.put(
                Role.EMPLOYEE,
                user -> () -> new EmployeeMenu(scanner, user).show()
        );

        roleMenus.put(
                Role.AGENT,
                user -> () -> new AgentMenu(scanner, user).show()
        );

        roleMenus.put(
                Role.CUSTOMER,
                user -> () -> new CustomerMenu(scanner, user).show()
        );
    }

    /**
     * Starts the insurance management application.
     */
    public void run() {

        try {

            while (true) {

                System.out.println();
                System.out.println("===== INSURANCE MANAGEMENT SYSTEM =====");
                System.out.println("1. Login");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");

                String choice = scanner.nextLine().trim();

                if (choice.equals("0")) {
                    System.out.println("Thank you for using the system.");
                    break;
                }

                if (choice.equals("1")) {
                    login();
                } else {
                    System.out.println(
                            "Invalid choice. Please enter 1 or 0."
                    );
                }
            }

        } finally {

            MyBatisUtil.close();
            scanner.close();
        }
    }

    /**
     * Handles role-based login.
     *
     * The user first selects the role and then enters
     * username and password.
     */
    private void login() {

        try {

            /*
             * Ask the user to select the role before
             * asking for username and password.
             */
            Role selectedRole = readLoginRole();

            System.out.print("Username: ");
            String username = scanner.nextLine().trim();

            if (username.isEmpty()) {
                System.out.println(
                        "Username cannot be empty. Please try again."
                );
                return;
            }

            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (password.isEmpty()) {
                System.out.println(
                        "Password cannot be empty. Please try again."
                );
                return;
            }

            /*
             * Authenticate the username and password.
             */
            Optional<User> userOptional =
                    authService.login(username, password);

            if (userOptional.isEmpty()) {

                System.out.println(
                        "Invalid username or password. Please try again."
                );

                return;
            }

            User user = userOptional.get();

            /*
             * Check whether the role selected during login
             * matches the role stored in the database.
             */
            if (user.getRole() != selectedRole) {

                System.out.println(
                        "Role does not match this username. "
                                + "Please try again."
                );

                return;
            }

            System.out.println();
            System.out.println(
                    "Login successful. Welcome, "
                            + user.getName()
                            + "!"
            );

            /*
             * Get the menu function for the user's role.
             */
            Function<User, Runnable> menuFunction =
                    roleMenus.get(user.getRole());

            if (menuFunction == null) {

                System.out.println(
                        "No menu is configured for this role."
                );

                return;
            }

            /*
             * Create and execute the appropriate menu.
             */
            Runnable menu = menuFunction.apply(user);

            menu.run();

        } catch (RuntimeException e) {

            System.out.println();
            System.out.println(
                    "Login failed: " + e.getMessage()
            );
        }
    }

    /**
     * Displays the role selection menu.
     *
     * The method keeps asking until the user enters
     * a valid role.
     */
    private Role readLoginRole() {

        while (true) {

            System.out.println();
            System.out.println("===== SELECT LOGIN ROLE =====");
            System.out.println("1. Admin");
            System.out.println("2. Employee");
            System.out.println("3. Agent");
            System.out.println("4. Customer");
            System.out.print("Enter role: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1":
                    return Role.ADMIN;

                case "2":
                    return Role.EMPLOYEE;

                case "3":
                    return Role.AGENT;

                case "4":
                    return Role.CUSTOMER;

                default:
                    System.out.println(
                            "Invalid role. "
                                    + "Please select 1, 2, 3 or 4."
                    );
            }
        }
    }
}