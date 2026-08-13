package com.crimsonlogic.insurancemanagementsystem.validator;

import com.crimsonlogic.insurancemanagementsystem.exception.InvalidInputException;
import com.crimsonlogic.insurancemanagementsystem.model.Employee;

public class EmployeeValidator {
    public void validate(Employee e) throws InvalidInputException {
        if (e == null||blank(e.getName())||e.getName().length()<2||blank(e.getUsername())||e.getUsername().length()<4||blank(e.getPassword())||e.getPassword().length()<6||!email(e.getEmail())||!phone(e.getPhone())) throw new InvalidInputException("Employee: name>=2 chars, username>=4, password>=6, valid email and 10-digit phone are required.");
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
