package com.crimsonlogic.insurancemanagementsystem.model;

import com.crimsonlogic.insurancemanagementsystem.enums.*;

public class Claim {
    private String id, policyId, customerId, agentId, description;
    private double amount;
    private ClaimStatus status;
    public Claim() {
    }
    public String getId() {
        return id;
    }
    public void setId(String v) {
        id = v;
    }
    public String getPolicyId() {
        return policyId;
    }
    public void setPolicyId(String v) {
        policyId = v;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String v) {
        customerId = v;
    }
    public String getAgentId() {
        return agentId;
    }
    public void setAgentId(String v) {
        agentId = v;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String v) {
        description = v;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double v) {
        amount = v;
    }
    public ClaimStatus getStatus() {
        return status;
    }
    public void setStatus(ClaimStatus v) {
        status = v;
    }
}
