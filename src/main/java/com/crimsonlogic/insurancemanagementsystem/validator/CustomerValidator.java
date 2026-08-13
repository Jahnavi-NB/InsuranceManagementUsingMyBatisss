package com.crimsonlogic.insurancemanagementsystem.validator;

import com.crimsonlogic.insurancemanagementsystem.exception.InvalidInputException;
import com.crimsonlogic.insurancemanagementsystem.model.Customer;

public class CustomerValidator {
    public void validate(Customer c) throws InvalidInputException {
        if (c == null||blank(c.getName())||blank(c.getUsername())||c.getUsername().length()<4||blank(c.getPassword())||c.getPassword().length()<6||c.getAge()<18||c.getAge()>100||!email(c.getEmail())||!phone(c.getPhone())) throw new InvalidInputException("Customer: age 18-100, username>=4, password>=6, valid email and 10-digit phone are required.");
    }
    private boolean blank(String s) {
        return s == null||s.isBlank();
    }
    private boolean email(String s) {
        return s != null&&s.matches("^[^@\s]+@[^@\s]+\\.[^@\s]+$");
    }
    private boolean phone(String s) {
        return s != null&&s.matches("\\d{10}");
    }
}
