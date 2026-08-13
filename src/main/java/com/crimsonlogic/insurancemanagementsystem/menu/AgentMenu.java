package com.crimsonlogic.insurancemanagementsystem.menu;

import com.crimsonlogic.insurancemanagementsystem.service.*;
import com.crimsonlogic.insurancemanagementsystem.service.impl.*;
import com.crimsonlogic.insurancemanagementsystem.model.*;
import com.crimsonlogic.insurancemanagementsystem.util.*;
import java.util.*;

public class AgentMenu {
    private final Scanner scanner;
    private final User user;
    private final ClaimService claimService = new ClaimServiceImpl();
    public AgentMenu(Scanner scanner, User user) {
        this.scanner = scanner;
        this.user = user;
    }
    public void show() {
        while (true) {
            try {
                System.out.println("\n===== AGENT MENU =====\n1 View My Claims\n2 Approve Claim\n3 Reject Claim\n0 Logout");
                String c = scanner.nextLine().trim();
                if (c.equals("0")) return;
                switch (c) {
                    case "1" -> view();
                    case "2" -> decide(true);
                    case "3" -> decide(false);
                    default -> System.out.println("Invalid choice. Enter 0-3.");
                }
            } catch (Exception e) {
                System.out.println("Agent operation failed: "+e.getMessage());
            }
        }
    }
    private void view() {
        List<Claim> l = claimService.byAgent(user.getId());
        List<String[]> r = new ArrayList<>();
        for (Claim c:l)r.add(new String[] {
            c.getId(), c.getPolicyId(), String.format("%.2f", c.getAmount()), c.getStatus().name()
        }
        );
        if (r.isEmpty())TableFormatter.empty();
        else TableFormatter.print("MY CLAIMS", new String[] {
            "CLAIM ID", "POLICY ID", "AMOUNT", "STATUS"
        }
        , r);
    }
    private void decide(boolean approve) throws Exception {
        while (true) {
            try {
                System.out.print("Claim ID: ");
                String id = scanner.nextLine().trim();
                if (id.isBlank()) {
                    System.out.println("Claim ID cannot be blank.");
                    break;
                }
                if (approve)claimService.approve(id, user.getId());
                else claimService.reject(id, user.getId());
                System.out.println("Claim processed successfully.");
                return;
            } catch (Exception e) {
                System.out.println("Unable to process claim: "+e.getMessage()+" Please try again.");
            }
        }
    }
}
