package com.crimsonlogic.insurancemanagementsystem.model;

import com.crimsonlogic.insurancemanagementsystem.enums.*;

public class Admin extends User {
    public Admin() {
    }
    public Admin(String id, String name, String username, String password, String email, String phone, Status status) {
        super(id, name, username, password, email, phone, Role.ADMIN, status);
    }
}
