package com.crimsonlogic.insurancemanagementsystem.menu;

import com.crimsonlogic.insurancemanagementsystem.exception.DatabaseOperationException;
import com.crimsonlogic.insurancemanagementsystem.service.*;
import com.crimsonlogic.insurancemanagementsystem.service.impl.*;
import com.crimsonlogic.insurancemanagementsystem.model.*;
import com.crimsonlogic.insurancemanagementsystem.enums.*;
import com.crimsonlogic.insurancemanagementsystem.util.*;
import java.util.*;

public class CustomerMenu {
    private final Scanner scanner;
    private final User user;
    private final PolicyService policyService = new PolicyServiceImpl();
    private final PaymentService paymentService = new PaymentServiceImpl();
    private final ClaimService claimService = new ClaimServiceImpl();
    public CustomerMenu(Scanner scanner, User user) {
        this.scanner = scanner;
        this.user = user;
    }
    public void show() {
        while (true) {
            try {
                System.out.println("\n===== CUSTOMER MENU =====\n" +
                        "1 View Policies\n" +
                        "2 Take Policy\n" +
                        "3 Pay Premium\n" +
                        "4 Payment History\n" +
                        "5 Submit Claim\n" +
                        "6 View My Policies" +
                        "\n7 View My Claims\n" +
                        "8. Generate Reports \n 0 Logout");
                String c = scanner.nextLine().trim();


                if (c.equals("0")) return;
                switch (c) {
                    case "1" -> policies();
                    case "2" -> purchase();
                    case "3" -> payment();
                    case "4" -> history();
                    case "5" -> claim();
                    case "6" -> myPolicies();
                    case "7" -> claims();
                    case "8" -> streamReports();
                    default -> System.out.println("Invalid choice. Enter 0-7.");
                }
            } catch (Exception e) {
                System.out.println("Customer operation failed: "+e.getMessage());
            }
        }
    }
    private void policies() throws DatabaseOperationException {
        List<Policy> list = policyService.findAll();
        List<String[]> rows = new ArrayList<>();
        for (Policy p:list)rows.add(new String[] {
            p.getId(), p.getName(), p.getType().name(), String.format("%.2f", p.getCoverageAmount()), String.format("%.2f", p.getPremium()), p.getCustomerId() == null?"AVAILABLE":"TAKEN"
        }
        );
        if (rows.isEmpty())TableFormatter.empty();
        else TableFormatter.print("POLICIES", new String[] {
            "ID", "NAME", "TYPE", "COVERAGE", "CURRENT PREMIUM", "AVAILABILITY"
        }
        , rows);
    }
    private void purchase() throws Exception {
        while (true) {
            try {
                System.out.print("Policy ID: ");
                String id = scanner.nextLine().trim();
                Policy p = policyService.purchasePolicy(user.getId(), id);
                System.out.println("Policy purchased successfully. Agent assigned automatically: "+p.getAgentId()+". Age-based premium: "+String.format("%.2f", p.getPremium()));
                return;
            } catch (Exception e) {
                System.out.println("Invalid purchase: "+e.getMessage()+" Please try again.");
            }
        }
    }
    private void payment() throws Exception {

        while (true) {

            try {

                System.out.println("\n===== PAY PREMIUM =====");

                System.out.print("Policy ID: ");
                String id = scanner.nextLine().trim();

                /*
                 * Find the customer's policy first.
                 */
                Optional<Policy> optionalPolicy =
                        policyService.findById(id);

                if (optionalPolicy.isEmpty()) {

                    System.out.println("Policy not found. Please try again.");

                    continue;
                }

                Policy policy = optionalPolicy.get();

                /*
                 * Display the premium calculated for
                 * this policy.
                 */
                System.out.printf("Premium Amount: ₹%.2f%n", policy.getPremium());

                /*
                 * Check whether the premium has already
                 * been paid.
                 */
                if (policy.getPremiumStatus()
                        == PremiumStatus.PAID) {

                    System.out.println("This premium has already been paid.");
                    break;
                }

                /*
                 * Ask the customer to enter the amount.
                 */
                double amount =
                        Double.parseDouble(read("Amount"));

                /*
                 * Payment method is an enum.
                 */
                PaymentMethod method =
                        PaymentMethod.valueOf(
                                read(
                                        "Payment Method " +
                                                "(CASH/CARD/UPI/NET_BANKING)"
                                )
                                        .trim()
                                        .toUpperCase()
                        );

                /*
                 * Send payment to PaymentService.
                 * PaymentService will verify that the
                 * amount matches the premium.
                 */
                paymentService.payPremium(
                        user.getId(),
                        id,
                        amount,
                        method
                );

                System.out.println(
                        "Payment successful."
                );

                return;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Amount must be numeric. Please try again."
                );

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Invalid payment method. " +
                                "Please use CASH, CARD, UPI or NET_BANKING."
                );

            } catch (Exception e) {

                System.out.println(
                        "Payment failed: "
                                + e.getMessage()
                                + " Please try again."
                );
            }
        }
    }
    private void history() {
        List<Payment> list = paymentService.history(user.getId());
        List<String[]>rows = new ArrayList<>();
        for (Payment p:list)
            rows.add(new String[] {
            p.getId(), p.getPolicyId(),
                            String.format("%.2f", p.getAmount()), p.getMethod().name(), p.getStatus().name(), String.valueOf(p.getPaidAt())
        }
        );
        if (rows.isEmpty())TableFormatter.empty();
        else TableFormatter.print("PAYMENT HISTORY", new String[] {
            "ID", "POLICY", "AMOUNT", "METHOD", "STATUS", "PAID AT"
        }
        , rows);
    }
    private void claim() throws Exception {
        while (true) {
            try {
                Claim c = new Claim();
                c.setCustomerId(user.getId());
                c.setPolicyId(read("Policy ID"));

                c.setAmount(Double.parseDouble(read("Claim Amount")));
                c.setDescription(read("Description"));

                claimService.submit(c);
                System.out.println("Claim submitted successfully." +
                        " Agent assigned: "+c.getAgentId());
                return;
            } catch (NumberFormatException e) {
                System.out.println("Claim amount must be " +
                        "numeric. Please try again.");
            } catch (Exception e) {
                System.out.println("Invalid claim: "
                        +e.getMessage()+" Please try again.");
            }
        }
    }
    private void myPolicies() throws DatabaseOperationException {
        List<String[]> r = new ArrayList<>();
        for (Policy p:policyService.findByCustomer(user.getId()))
            r.add(new String[] {
            p.getId(), p.getName(), p.getType().name(),
                            String.format("%.2f", p.getPremium()), p.getPremiumStatus().name(), p.getAgentId(), p.getStatus().name()
        }
        );
        if (r.isEmpty())TableFormatter.empty();
        else TableFormatter.print("MY POLICIES", new String[] {
            "ID", "NAME", "TYPE", "PREMIUM", "PREMIUM STATUS", "AGENT", "STATUS"
        }
        , r);
    }
    private void claims() {
        List<Claim>list = claimService.byCustomer(user.getId());
        List<String[]> r = new ArrayList<>();
        for (Claim c:list)
            r.add(new String[] {
            c.getId(), c.getPolicyId(),
                            String.format("%.2f", c.getAmount()),
                            c.getAgentId(), c.getStatus().name()
        }
        );
        if (r.isEmpty())TableFormatter.empty();
        else TableFormatter.print("MY CLAIMS", new String[] {
            "ID", "POLICY", "AMOUNT", "AGENT", "STATUS"
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
