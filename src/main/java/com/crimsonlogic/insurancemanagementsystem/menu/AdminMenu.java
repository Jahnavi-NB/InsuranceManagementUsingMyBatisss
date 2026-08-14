package com.crimsonlogic.insurancemanagementsystem.menu;

import com.crimsonlogic.insurancemanagementsystem.exception.DatabaseOperationException;
import com.crimsonlogic.insurancemanagementsystem.model.*;
import com.crimsonlogic.insurancemanagementsystem.enums.*;
import com.crimsonlogic.insurancemanagementsystem.service.*;
import com.crimsonlogic.insurancemanagementsystem.service.impl.*;
import com.crimsonlogic.insurancemanagementsystem.util.*;
import java.util.*;

public class AdminMenu {
    private final Scanner scanner;
    private final User user;
    private final UserService userService = new UserServiceImpl();
    private final PolicyService policyService = new PolicyServiceImpl();
    public AdminMenu(Scanner scanner, User user) {
        this.scanner = scanner;
        this.user = user;
    }
    public void show() {
        while (true) {
            try {
                System.out.println("\n===== ADMIN MENU =====\n" +
                        "1 Register Employee\n" +
                        "2 Register Agent\n3 " +
                        "View Users\n" +
                        "4 Change User Status\n5" +
                        " View Policies\n6.Generate Reports" +
                        "\n 0 Logout");
                String c = scanner.nextLine().trim();

                if (c.equals("0")) return;
                switch (c) {
                    case "1" -> registerEmployee();
                    case "2" -> registerAgent();
                    case "3" -> users();
                    case "4" -> status();
                    case "5" -> policies();
                    case "6" -> streamReports();
                    default -> System.out.println("Invalid choice. Enter 0-5.");
                }
            } catch (Exception e) {
                System.out.println("Admin operation failed: "+e.getMessage());
            }
        }
    }
    private void registerEmployee() {
        while (true) {
            try {
                Employee e = new Employee(UUID.randomUUID()
                        .toString(), read("Name"), read("Username"), read("Password"), read("Email"), read("Phone"), Status.ACTIVE);
                userService.registerEmployee(e);
                System.out.println("Employee registered successfully.");
                return;
            } catch (Exception e) {
                System.out.println("Invalid employee data: "
                        +e.getMessage()+" Please enter again.");
            }
        }
    }
    private void registerAgent() {
        while (true) {
            try {
                Agent a = new Agent(UUID.randomUUID()
                        .toString(), read("Name"),
                        read("Username"), read("Password"),
                        read("Email"), read("Phone"), Status.ACTIVE);
                userService.registerAgent(a);
                System.out.println("Agent registered successfully.");
                return;
            } catch (Exception e) {
                System.out.println("Invalid agent data: "+e.getMessage()+" Please enter again.");
            }
        }
    }
    private void users() {
        List<String[]> r = new ArrayList<>();
        try {
            for (User u:userService.findAll())r.add(new String[] {
                u.getId(), u.getName(),
                            u.getRole().name(), u.getUsername(),
                            u.getStatus().name()
            }
            );
        } catch (DatabaseOperationException e) {
            throw new RuntimeException(e);
        }
        if (r.isEmpty())TableFormatter.empty();
        else TableFormatter.print("USERS", new String[] {
            "ID", "NAME", "ROLE", "USERNAME", "STATUS"
        }
        , r);
    }
    private void status() {
        while (true) {
            try {
                String id = read("User ID");
                Status st = Status.valueOf(read("Status ACTIVE/INACTIVE")
                        .toUpperCase());
                userService.changeStatus(id, st);
                System.out.println("Status updated successfully.");
                return;

            } catch (IllegalArgumentException e) {
                System.out.println("Status must be ACTIVE or " +
                        "INACTIVE. Please try again.");
            } catch (Exception e) {
                System.out.println("Unable to update status: "
                        +e.getMessage()+" Please try again.");
            }
        }
    }
    private void policies() {
        List<String[]> r = new ArrayList<>();
        try {
            for (Policy p:policyService.findAll())r.add(new String[] {
                p.getId(), p.getName(), p.getType().name(), String.format("%.2f", p.getPremium()), p.getPremiumStatus().name(), p.getAgentId(), p.getStatus().name()
            }
            );
        } catch (DatabaseOperationException e) {
            throw new RuntimeException(e);
        }
        if (r.isEmpty())TableFormatter.empty();
        else TableFormatter.print("POLICIES", new String[] {
            "ID", "NAME", "TYPE", "PREMIUM", "PREMIUM STATUS", "AGENT", "STATUS"
        }
        , r);
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
