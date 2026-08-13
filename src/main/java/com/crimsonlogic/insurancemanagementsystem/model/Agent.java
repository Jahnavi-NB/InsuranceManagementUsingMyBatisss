package com.crimsonlogic.insurancemanagementsystem.model;

import com.crimsonlogic.insurancemanagementsystem.enums.*;

public class Agent extends User {
    public Agent() {
    }
    public Agent(String id, String name, String username, String password, String email, String phone, Status status) {
        super(id, name, username, password, email, phone, Role.AGENT, status);
    }
}
