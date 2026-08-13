package com.crimsonlogic.insurancemanagementsystem.validator;

import com.crimsonlogic.insurancemanagementsystem.exception.InvalidInputException;
import com.crimsonlogic.insurancemanagementsystem.model.Agent;

public class AgentValidator {
    public void validate(Agent a) throws InvalidInputException {
        if (a == null||blank(a.getName())||blank(a.getUsername())||a.getUsername().length()<4||blank(a.getPassword())||a.getPassword().length()<6||!email(a.getEmail())||!phone(a.getPhone())) throw new InvalidInputException("Agent: required fields, username>=4, password>=6, valid email and 10-digit phone are required.");
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
