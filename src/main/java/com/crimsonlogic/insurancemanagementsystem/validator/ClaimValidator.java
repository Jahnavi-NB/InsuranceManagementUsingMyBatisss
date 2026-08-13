package com.crimsonlogic.insurancemanagementsystem.validator;

import com.crimsonlogic.insurancemanagementsystem.exception.ClaimValidationException;
import com.crimsonlogic.insurancemanagementsystem.model.*;

public class ClaimValidator {
    public void validate(Claim c, Policy p) throws ClaimValidationException {
        if (c == null||p == null) throw new ClaimValidationException("Claim or policy not found.");
        if (c.getAmount()<=0||c.getAmount()>p.getCoverageAmount()) throw new ClaimValidationException("Claim amount must be positive and cannot exceed coverage.");
        if (p.getPremiumStatus() != com.crimsonlogic.insurancemanagementsystem.enums.PremiumStatus.PAID) throw new ClaimValidationException("Premium must be paid before claiming.");
        if (p.getStatus() != com.crimsonlogic.insurancemanagementsystem.enums.Status.ACTIVE) throw new ClaimValidationException("Policy is not active.");
    }
}
