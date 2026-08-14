package com.crimsonlogic.insurancemanagementsystem.menu;

import java.util.List;
import java.util.Scanner;

import com.crimsonlogic.insurancemanagementsystem.enums.Role;
import com.crimsonlogic.insurancemanagementsystem.model.User;
import com.crimsonlogic.insurancemanagementsystem.menu.StreamReport;
import com.crimsonlogic.insurancemanagementsystem.service.UserService;
import com.crimsonlogic.insurancemanagementsystem.service.impl.UserServiceImpl;

public class StreamReportMenu {

    private final Scanner scanner;

    private final UserService userService =
            new UserServiceImpl();

    public StreamReportMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void show(User user) {

        try {

            List<User> users = userService.findAll();

            StreamReport report =
                    new StreamReport(users);

            if (user.getRole() == Role.ADMIN) {

                showAdminReports(report);

            } else if (user.getRole() == Role.AGENT) {

                showAgentReports(report);

            } else if (user.getRole() == Role.EMPLOYEE) {

                showEmployeeReports(report);

            } else if (user.getRole() == Role.CUSTOMER) {

                showCustomerReports(report);

            } else {

                System.out.println(
                        "No reports available for this role."
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Unable to generate reports: "
                            + e.getMessage()
            );
        }
    }

    // =========================================================
    // ADMIN REPORT MENU
    // =========================================================

    private void showAdminReports(StreamReport report) {

        while (true) {

            System.out.println("\n================================");
            System.out.println("       ADMIN STREAM REPORTS");
            System.out.println("================================");

            System.out.println("1. All Users");
            System.out.println("2. Active Users");
            System.out.println("3. Inactive Users");
            System.out.println("4. Users By Role");
            System.out.println("5. Users By Status");
            System.out.println("6. Count Users By Role");
            System.out.println("7. Count Users By Status");
            System.out.println("8. All Agents");
            System.out.println("9. All Employees");
            System.out.println("10. All Customers");
            System.out.println("11. Count Agents");
            System.out.println("12. Count Employees");
            System.out.println("13. Count Customers");
            System.out.println("14. Sort Users By Name");
            System.out.println("15. Sort Users By ID");
            System.out.println("16. Distinct Roles");
            System.out.println("17. Distinct Statuses");
            System.out.println("18. Search User By Name");
            System.out.println("19. Search User By Username");
            System.out.println("20. Search User By Email");
            System.out.println("21. Check Admin Exists");
            System.out.println("22. Check All Users Active");
            System.out.println("0. Back");

            System.out.print("Enter choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1" -> report.allUsers();

                case "2" -> report.activeUsers();

                case "3" -> report.inactiveUsers();

                case "4" -> report.usersByRole();

                case "5" -> report.usersByStatus();

                case "6" -> report.countUsersByRole();

                case "7" -> report.countUsersByStatus();

                case "8" -> report.allAgents();

                case "9" -> report.allEmployees();

                case "10" -> report.allCustomers();

                case "11" -> report.countAgents();

                case "12" -> report.countEmployees();

                case "13" -> report.countCustomers();

                case "14" -> report.sortUsersByName();

                case "15" -> report.sortUsersById();

                case "16" -> report.distinctRoles();

                case "17" -> report.distinctStatuses();

                case "18" -> {

                    System.out.print("Enter name prefix: ");

                    String prefix =
                            scanner.nextLine();

                    report.usersStartingWith(prefix);
                }

                case "19" -> {

                    System.out.print("Enter username: ");

                    String username =
                            scanner.nextLine();

                    report.findByUsername(username);
                }

                case "20" -> {

                    System.out.print("Enter email: ");

                    String email =
                            scanner.nextLine();

                    report.findByEmail(email);
                }

                case "21" -> report.adminExists();

                case "22" -> report.allUsersActive();

                case "0" -> {
                    return;
                }

                default ->
                        System.out.println(
                                "Invalid choice."
                        );
            }
        }
    }

    // =========================================================
    // AGENT REPORT MENU
    // =========================================================

    private void showAgentReports(StreamReport report) {

        while (true) {

            System.out.println("\n================================");
            System.out.println("       AGENT STREAM REPORTS");
            System.out.println("================================");

            System.out.println("1. All Agents");
            System.out.println("2. Active Agents");
            System.out.println("3. Inactive Agents");
            System.out.println("4. Count Agents");
            System.out.println("5. Sort Users By Name");
            System.out.println("6. Distinct Statuses");
            System.out.println("0. Back");

            System.out.print("Enter choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1" -> report.allAgents();

                case "2" -> report.activeAgents();

                case "3" -> report.inactiveAgents();

                case "4" -> report.countAgents();

                case "5" -> report.sortUsersByName();

                case "6" -> report.distinctStatuses();

                case "0" -> {
                    return;
                }

                default ->
                        System.out.println(
                                "Invalid choice."
                        );
            }
        }
    }

    // =========================================================
    // EMPLOYEE REPORT MENU
    // =========================================================

    private void showEmployeeReports(StreamReport report) {

        while (true) {

            System.out.println("\n================================");
            System.out.println("      EMPLOYEE STREAM REPORTS");
            System.out.println("================================");

            System.out.println("1. All Employees");
            System.out.println("2. Active Employees");
            System.out.println("3. Inactive Employees");
            System.out.println("4. Count Employees");
            System.out.println("5. Sort Users By Name");
            System.out.println("6. Distinct Statuses");
            System.out.println("0. Back");

            System.out.print("Enter choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1" -> report.allEmployees();

                case "2" -> report.activeEmployees();

                case "3" -> report.inactiveEmployees();

                case "4" -> report.countEmployees();

                case "5" -> report.sortUsersByName();

                case "6" -> report.distinctStatuses();

                case "0" -> {
                    return;
                }

                default ->
                        System.out.println(
                                "Invalid choice."
                        );
            }
        }
    }

    // =========================================================
    // CUSTOMER REPORT MENU
    // =========================================================

    private void showCustomerReports(StreamReport report) {

        while (true) {

            System.out.println("\n================================");
            System.out.println("      CUSTOMER STREAM REPORTS");
            System.out.println("================================");

            System.out.println("1. My Customer Details");
            System.out.println("2. All Customers");
            System.out.println("3. Active Customers");
            System.out.println("4. Inactive Customers");
            System.out.println("5. Count Customers");
            System.out.println("6. Customer Age Report");
            System.out.println("7. Oldest Customer");
            System.out.println("8. Youngest Customer");
            System.out.println("9. Average Customer Age");
            System.out.println("10. Customer Age Statistics");
            System.out.println("0. Back");

            System.out.print("Enter choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1" ->
                        System.out.println(
                                "Use your customer details option "
                                        + "from the Customer Menu."
                        );

                case "2" -> report.allCustomers();

                case "3" -> report.activeCustomers();

                case "4" -> report.inactiveCustomers();

                case "5" -> report.countCustomers();

                case "6" -> report.customerAgeReport();

                case "7" -> report.oldestCustomer();

                case "8" -> report.youngestCustomer();

                case "9" -> report.averageCustomerAge();

                case "10" -> report.customerAgeStatistics();

                case "0" -> {
                    return;
                }

                default ->
                        System.out.println(
                                "Invalid choice."
                        );
            }
        }
    }
}