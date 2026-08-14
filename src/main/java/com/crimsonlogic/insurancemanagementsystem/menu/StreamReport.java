package com.crimsonlogic.insurancemanagementsystem.menu;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.crimsonlogic.insurancemanagementsystem.enums.Role;
import com.crimsonlogic.insurancemanagementsystem.enums.Status;
import com.crimsonlogic.insurancemanagementsystem.model.Agent;
import com.crimsonlogic.insurancemanagementsystem.model.Customer;
import com.crimsonlogic.insurancemanagementsystem.model.Employee;
import com.crimsonlogic.insurancemanagementsystem.model.User;
import com.crimsonlogic.insurancemanagementsystem.util.TableFormatter;

public class StreamReport {

    private List<User> users;

    public StreamReport(List<User> users) {
        this.users = users;
    }

    // =========================================================
    // COMMON REPORTS
    // =========================================================

    public void allUsers() {

        System.out.println("\n========== ALL USERS ==========");

        List<String[]> rows = users.stream()
                .map(this::userRow)
                .collect(Collectors.toList());

        printUsers("ALL USERS", rows);
    }

    public void activeUsers() {

        System.out.println("\n========== ACTIVE USERS ==========");

        List<String[]> rows = users.stream()
                .filter(u -> u.getStatus() == Status.ACTIVE)
                .map(this::userRow)
                .collect(Collectors.toList());

        printUsers("ACTIVE USERS", rows);
    }

    public void inactiveUsers() {

        System.out.println("\n========== INACTIVE USERS ==========");

        List<String[]> rows = users.stream()
                .filter(u -> u.getStatus() == Status.INACTIVE)
                .map(this::userRow)
                .collect(Collectors.toList());

        printUsers("INACTIVE USERS", rows);
    }

    public void usersByRole() {

        System.out.println("\n========== USERS BY ROLE ==========");

        Map<Role, List<User>> result =
                users.stream()
                        .filter(u -> u.getRole() != null)
                        .collect(Collectors.groupingBy(User::getRole));

        result.forEach((role, list) -> {

            System.out.println("\nRole : " + role);

            List<String[]> rows = list.stream()
                    .map(this::userRow)
                    .collect(Collectors.toList());

            printUsers(role + " USERS", rows);
        });
    }

    public void usersByStatus() {

        System.out.println("\n========== USERS BY STATUS ==========");

        Map<Status, List<User>> result =
                users.stream()
                        .filter(u -> u.getStatus() != null)
                        .collect(Collectors.groupingBy(User::getStatus));

        result.forEach((status, list) -> {

            System.out.println("\nStatus : " + status);

            List<String[]> rows = list.stream()
                    .map(this::userRow)
                    .collect(Collectors.toList());

            printUsers(status + " USERS", rows);
        });
    }

    public void countUsersByRole() {

        System.out.println("\n========== COUNT USERS BY ROLE ==========");

        users.stream()
                .filter(u -> u.getRole() != null)
                .collect(Collectors.groupingBy(
                        User::getRole,
                        Collectors.counting()
                ))
                .forEach((role, count) ->
                        System.out.println(role + " : " + count)
                );
    }

    public void countUsersByStatus() {

        System.out.println("\n========== COUNT USERS BY STATUS ==========");

        users.stream()
                .filter(u -> u.getStatus() != null)
                .collect(Collectors.groupingBy(
                        User::getStatus,
                        Collectors.counting()
                ))
                .forEach((status, count) ->
                        System.out.println(status + " : " + count)
                );
    }

    // =========================================================
    // AGENT REPORTS
    // =========================================================

    public void allAgents() {

        System.out.println("\n========== ALL AGENTS ==========");

        List<String[]> rows = users.stream()
                .filter(u -> u instanceof Agent)
                .map(this::userRow)
                .collect(Collectors.toList());

        printUsers("ALL AGENTS", rows);
    }

    public void activeAgents() {

        System.out.println("\n========== ACTIVE AGENTS ==========");

        List<String[]> rows = users.stream()
                .filter(u -> u instanceof Agent)
                .filter(u -> u.getStatus() == Status.ACTIVE)
                .map(this::userRow)
                .collect(Collectors.toList());

        printUsers("ACTIVE AGENTS", rows);
    }

    public void inactiveAgents() {

        System.out.println("\n========== INACTIVE AGENTS ==========");

        List<String[]> rows = users.stream()
                .filter(u -> u instanceof Agent)
                .filter(u -> u.getStatus() == Status.INACTIVE)
                .map(this::userRow)
                .collect(Collectors.toList());

        printUsers("INACTIVE AGENTS", rows);
    }

    public void countAgents() {

        long count = users.stream()
                .filter(u -> u instanceof Agent)
                .count();

        System.out.println("\nTotal Agents : " + count);
    }

    // =========================================================
    // EMPLOYEE REPORTS
    // =========================================================

    public void allEmployees() {

        System.out.println("\n========== ALL EMPLOYEES ==========");

        List<String[]> rows = users.stream()
                .filter(u -> u instanceof Employee)
                .map(this::userRow)
                .collect(Collectors.toList());

        printUsers("ALL EMPLOYEES", rows);
    }

    public void activeEmployees() {

        System.out.println("\n========== ACTIVE EMPLOYEES ==========");

        List<String[]> rows = users.stream()
                .filter(u -> u instanceof Employee)
                .filter(u -> u.getStatus() == Status.ACTIVE)
                .map(this::userRow)
                .collect(Collectors.toList());

        printUsers("ACTIVE EMPLOYEES", rows);
    }

    public void inactiveEmployees() {

        System.out.println("\n========== INACTIVE EMPLOYEES ==========");

        List<String[]> rows = users.stream()
                .filter(u -> u instanceof Employee)
                .filter(u -> u.getStatus() == Status.INACTIVE)
                .map(this::userRow)
                .collect(Collectors.toList());

        printUsers("INACTIVE EMPLOYEES", rows);
    }

    public void countEmployees() {

        long count = users.stream()
                .filter(u -> u instanceof Employee)
                .count();

        System.out.println("\nTotal Employees : " + count);
    }

    // =========================================================
    // CUSTOMER REPORTS
    // =========================================================

    public void allCustomers() {

        System.out.println("\n========== ALL CUSTOMERS ==========");

        List<String[]> rows = users.stream()
                .filter(u -> u instanceof Customer)
                .map(this::userRow)
                .collect(Collectors.toList());

        printUsers("ALL CUSTOMERS", rows);
    }

    public void activeCustomers() {

        System.out.println("\n========== ACTIVE CUSTOMERS ==========");

        List<String[]> rows = users.stream()
                .filter(u -> u instanceof Customer)
                .filter(u -> u.getStatus() == Status.ACTIVE)
                .map(this::userRow)
                .collect(Collectors.toList());

        printUsers("ACTIVE CUSTOMERS", rows);
    }

    public void inactiveCustomers() {

        System.out.println("\n========== INACTIVE CUSTOMERS ==========");

        List<String[]> rows = users.stream()
                .filter(u -> u instanceof Customer)
                .filter(u -> u.getStatus() == Status.INACTIVE)
                .map(this::userRow)
                .collect(Collectors.toList());

        printUsers("INACTIVE CUSTOMERS", rows);
    }

    public void countCustomers() {

        long count = users.stream()
                .filter(u -> u instanceof Customer)
                .count();

        System.out.println("\nTotal Customers : " + count);
    }

    // =========================================================
    // CUSTOMER AGE REPORTS
    // =========================================================

    public void customerAgeReport() {

        System.out.println("\n========== CUSTOMER AGE REPORT ==========");

        users.stream()
                .filter(u -> u instanceof Customer)
                .map(u -> (Customer) u)
                .forEach(c ->
                        System.out.println(
                                "Name : " + c.getName()
                                        + " | Age : " + c.getAge()
                        )
                );
    }

    public void oldestCustomer() {

        System.out.println("\n========== OLDEST CUSTOMER ==========");

        Customer customer = users.stream()
                .filter(u -> u instanceof Customer)
                .map(u -> (Customer) u)
                .max(Comparator.comparingInt(Customer::getAge))
                .orElse(null);

        if (customer != null) {
            System.out.println(
                    "Name : " + customer.getName()
                            + " | Age : " + customer.getAge()
            );
        } else {
            System.out.println("No customers found.");
        }
    }

    public void youngestCustomer() {

        System.out.println("\n========== YOUNGEST CUSTOMER ==========");

        Customer customer = users.stream()
                .filter(u -> u instanceof Customer)
                .map(u -> (Customer) u)
                .min(Comparator.comparingInt(Customer::getAge))
                .orElse(null);

        if (customer != null) {
            System.out.println(
                    "Name : " + customer.getName()
                            + " | Age : " + customer.getAge()
            );
        } else {
            System.out.println("No customers found.");
        }
    }

    public void averageCustomerAge() {

        System.out.println("\n========== AVERAGE CUSTOMER AGE ==========");

        double average = users.stream()
                .filter(u -> u instanceof Customer)
                .map(u -> (Customer) u)
                .mapToInt(Customer::getAge)
                .average()
                .orElse(0);

        System.out.println("Average Customer Age : " + average);
    }

    public void customerAgeStatistics() {

        System.out.println("\n========== CUSTOMER AGE STATISTICS ==========");

        java.util.IntSummaryStatistics stats = users.stream()
                .filter(u -> u instanceof Customer)
                .map(u -> (Customer) u)
                .mapToInt(Customer::getAge)
                .summaryStatistics();

        System.out.println("Count   : " + stats.getCount());
        System.out.println("Average : " + stats.getAverage());
        System.out.println("Maximum : " + stats.getMax());
        System.out.println("Minimum : " + stats.getMin());
    }

    // =========================================================
    // SORTING
    // =========================================================

    public void sortUsersByName() {

        System.out.println("\n========== USERS SORTED BY NAME ==========");

        List<String[]> rows = users.stream()
                .sorted(
                        Comparator.comparing(
                                User::getName,
                                String.CASE_INSENSITIVE_ORDER
                        )
                )
                .map(this::userRow)
                .collect(Collectors.toList());

        printUsers("USERS SORTED BY NAME", rows);
    }

    public void sortUsersById() {

        System.out.println("\n========== USERS SORTED BY ID ==========");

        List<String[]> rows = users.stream()
                .sorted(Comparator.comparing(User::getId))
                .map(this::userRow)
                .collect(Collectors.toList());

        printUsers("USERS SORTED BY ID", rows);
    }

    // =========================================================
    // DISTINCT VALUES
    // =========================================================

    public void distinctRoles() {

        System.out.println("\n========== DISTINCT ROLES ==========");

        users.stream()
                .map(User::getRole)
                .filter(Objects::nonNull)
                .distinct()
                .forEach(System.out::println);
    }

    public void distinctStatuses() {

        System.out.println("\n========== DISTINCT STATUSES ==========");

        users.stream()
                .map(User::getStatus)
                .filter(Objects::nonNull)
                .distinct()
                .forEach(System.out::println);
    }

    // =========================================================
    // SEARCH
    // =========================================================

    public void usersStartingWith(String prefix) {

        System.out.println(
                "\n========== USERS STARTING WITH "
                        + prefix + " =========="
        );

        List<String[]> rows = users.stream()
                .filter(u ->
                        u.getName() != null &&
                                u.getName()
                                        .toLowerCase()
                                        .startsWith(prefix.toLowerCase())
                )
                .map(this::userRow)
                .collect(Collectors.toList());

        printUsers("SEARCH RESULT", rows);
    }

    public void findByUsername(String username) {

        System.out.println("\n========== USER SEARCH ==========");

        Optional<User> result = users.stream()
                .filter(u ->
                        u.getUsername() != null &&
                                u.getUsername()
                                        .equalsIgnoreCase(username)
                )
                .findFirst();

        if (result.isPresent()) {
            System.out.println(
                    "ID       : " + result.get().getId()
            );
            System.out.println(
                    "Name     : " + result.get().getName()
            );
            System.out.println(
                    "Username : " + result.get().getUsername()
            );
            System.out.println(
                    "Email    : " + result.get().getEmail()
            );
            System.out.println(
                    "Phone    : " + result.get().getPhone()
            );
            System.out.println(
                    "Role     : " + result.get().getRole()
            );
            System.out.println(
                    "Status   : " + result.get().getStatus()
            );
        } else {
            System.out.println("User not found.");
        }
    }

    public void findByEmail(String email) {

        System.out.println("\n========== EMAIL SEARCH ==========");

        Optional<User> result = users.stream()
                .filter(u ->
                        u.getEmail() != null &&
                                u.getEmail()
                                        .equalsIgnoreCase(email)
                )
                .findFirst();

        if (result.isPresent()) {
            System.out.println(
                    "ID       : " + result.get().getId()
            );
            System.out.println(
                    "Name     : " + result.get().getName()
            );
            System.out.println(
                    "Username : " + result.get().getUsername()
            );
            System.out.println(
                    "Email    : " + result.get().getEmail()
            );
            System.out.println(
                    "Phone    : " + result.get().getPhone()
            );
            System.out.println(
                    "Role     : " + result.get().getRole()
            );
            System.out.println(
                    "Status   : " + result.get().getStatus()
            );
        } else {
            System.out.println("User not found.");
        }
    }

    // =========================================================
    // BOOLEAN OPERATIONS
    // =========================================================

    public void adminExists() {

        boolean result = users.stream()
                .anyMatch(u -> u.getRole() == Role.ADMIN);

        System.out.println(
                result
                        ? "Admin exists."
                        : "No Admin found."
        );
    }

    public void allUsersActive() {

        boolean result = users.stream()
                .allMatch(u -> u.getStatus() == Status.ACTIVE);

        System.out.println(
                result
                        ? "All users are ACTIVE."
                        : "Some users are INACTIVE."
        );
    }

    // =========================================================
    // HELPER METHODS
    // =========================================================

    private String[] userRow(User u) {

        return new String[]{
                u.getId(),
                u.getName(),
                u.getUsername(),
                u.getEmail(),
                u.getPhone(),
                u.getRole() != null ? u.getRole().name() : "",
                u.getStatus() != null ? u.getStatus().name() : ""
        };
    }

    private void printUsers(String title, List<String[]> rows) {

        if (rows.isEmpty()) {
            TableFormatter.empty();
            return;
        }

        TableFormatter.print(
                title,
                new String[]{
                        "ID",
                        "NAME",
                        "USERNAME",
                        "EMAIL",
                        "PHONE",
                        "ROLE",
                        "STATUS"
                },
                rows
        );
    }
}