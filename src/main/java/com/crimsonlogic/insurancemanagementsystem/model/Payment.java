package com.crimsonlogic.insurancemanagementsystem.model;

import com.crimsonlogic.insurancemanagementsystem.enums.*;
import java.time.LocalDateTime;

public class Payment {
    private String id, policyId, customerId;
    private double amount;
    private PaymentMethod method;
    private PaymentStatus status;
    private LocalDateTime paidAt;
    public Payment() {
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
    public double getAmount() {
        return amount;
    }
    public void setAmount(double v) {
        amount = v;
    }
    public PaymentMethod getMethod() {
        return method;
    }
    public void setMethod(PaymentMethod v) {
        method = v;
    }
    public PaymentStatus getStatus() {
        return status;
    }
    public void setStatus(PaymentStatus v) {
        status = v;
    }
    public LocalDateTime getPaidAt() {
        return paidAt;
    }
    public void setPaidAt(LocalDateTime v) {
        paidAt = v;
    }
}
