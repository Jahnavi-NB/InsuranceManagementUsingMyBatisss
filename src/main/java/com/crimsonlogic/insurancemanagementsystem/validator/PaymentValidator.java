package com.crimsonlogic.insurancemanagementsystem.validator;

import com.crimsonlogic.insurancemanagementsystem.exception.PaymentValidationException;
import com.crimsonlogic.insurancemanagementsystem.model.Payment;

public class PaymentValidator {
    public void validate(Payment p) throws PaymentValidationException {
        if (p == null||p.getAmount()<=0||p.getMethod() == null) throw new PaymentValidationException("Payment amount must be positive and payment method is required.");
    }
}
