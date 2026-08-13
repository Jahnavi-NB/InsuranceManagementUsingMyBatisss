package com.crimsonlogic.insurancemanagementsystem.model;

import com.crimsonlogic.insurancemanagementsystem.enums.*;

public class Policy {
    private String id, name, description, customerId, agentId;
    private PolicyType type;
    private double coverageAmount, premium;
    private PremiumStatus premiumStatus;
    private Status status;
    public Policy() {
    }
    public String getId() {
        return id;
    }
    public void setId(String v) {
        id = v;
    }
    public String getName() {
        return name;
    }
    public void setName(String v) {
        name = v;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String v) {
        description = v;
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
    public PolicyType getType() {
        return type;
    }
    public void setType(PolicyType v) {
        type = v;
    }
    public double getCoverageAmount() {
        return coverageAmount;
    }
    public void setCoverageAmount(double v) {
        coverageAmount = v;
    }
    public double getPremium() {
        return premium;
    }
    public void setPremium(double v) {
        premium = v;
    }
    public PremiumStatus getPremiumStatus() {
        return premiumStatus;
    }
    public void setPremiumStatus(PremiumStatus v) {
        premiumStatus = v;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status v) {
        status = v;
    }
}
