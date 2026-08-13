package com.crimsonlogic.insurancemanagementsystem.validator;

import com.crimsonlogic.insurancemanagementsystem.exception.InvalidInputException;
import com.crimsonlogic.insurancemanagementsystem.model.Policy;

public class PolicyValidator {
    public void validate(Policy p) throws InvalidInputException {
        if (p == null||p.getName() == null||p.getName().isBlank()||p.getCoverageAmount()<=0||p.getPremium()<=0||p.getType() == null) throw new InvalidInputException("Invalid policy data.");
    }
}
