package com.crimsonlogic.insurancemanagementsystem.menu;

import com.crimsonlogic.insurancemanagementsystem.exception.DatabaseOperationException;
import com.crimsonlogic.insurancemanagementsystem.model.*;
import com.crimsonlogic.insurancemanagementsystem.enums.*;
import com.crimsonlogic.insurancemanagementsystem.service.*;
import com.crimsonlogic.insurancemanagementsystem.service.impl.*;
import com.crimsonlogic.insurancemanagementsystem.util.*;
import java.util.*;

public class EmployeeMenu {
    private final Scanner scanner;
    private final User user;
    private final UserService userService = new UserServiceImpl();
    public EmployeeMenu(Scanner scanner, User user) {
        this.scanner = scanner;
        this.user = user;
    }
    public void show() {
        while (true) {
            try {
                System.out.println("\n===== EMPLOYEE MENU =====\n" +
                        "1 Register Customer\n" +
                        "2 View Customers\n" +
                        "3 Change Customer Status\n" +
                        "4.Generate Reports \n 0 Logout");
                String c = scanner.nextLine().trim();
                if (c.equals("0")) return;
                switch (c) {
                    case "1" -> register();
                    case "2" -> view();
                    case "3" -> status();
                    case "4" -> streamReports();
                    default -> System.out.println("Invalid choice. Enter 0-3.");
                }
            } catch (Exception e) {
                System.out.println("Employee operation failed: "+e.getMessage());
            }
        }
    }
    private void register() {
        while (true) {
            try {
                int age = Integer.parseInt(read("Age"));

                Customer c = new Customer(
                        UUID.randomUUID().toString(),
                        read("Name"),
                        read("Username"),
                        read("Password"),
                        read("Email"),
                        read("Phone"),
                        age,
                        Status.ACTIVE
                );

                userService.registerCustomer(c);

                System.out.println("Customer registered successfully.");
                return;

            } catch (NumberFormatException e) {
                System.out.println(
                        "Age must be a whole number. Please enter the customer again."
                );

            } catch (Exception e) {
                System.out.println(
                        "Invalid customer data: "
                                + e.getMessage()
                                + " Please enter again."
                );
            }
        }
    }
    private void view() throws DatabaseOperationException {
        List<String[]> r = new ArrayList<>();


        for (User u:userService.findAll())
            if (u.getRole() == Role.CUSTOMER)
                r.add(new String[] {
            u.getId(), u.getName(), u.getUsername(),
                                u.getEmail(), u.getPhone(), u.getStatus().name() }
        );
        if (r.isEmpty())
            TableFormatter.empty();

        else
            TableFormatter.print("CUSTOMERS", new String[] {
            "ID", "NAME", "USERNAME", "EMAIL", "PHONE", "STATUS"
        }, r);
    }
    private void status() {
        while (true) {
            try {

                userService.changeStatus(read("Customer ID"),
                        Status.valueOf(read("Status ACTIVE/INACTIVE")
                                .toUpperCase()));
                System.out.println("Status updated successfully.");
                return;

            } catch (IllegalArgumentException e) {

                System.out.println("Status must be ACTIVE " +
                        "or INACTIVE. Please try again.");
            } catch (Exception e) {
                System.out.println("Unable to update status: "
                        +e.getMessage()+" Please try again.");
            }
        }
    }
    private String read(String label) {
        System.out.print(label+": ");
        return scanner.nextLine().trim();
    }
    private void streamReports() {

        StreamReportMenu menu =
                new StreamReportMenu(scanner);

        menu.show(user);
    }
}
