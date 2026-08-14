package com.crimsonlogic.insurancemanagementsystem.model;

import com.crimsonlogic.insurancemanagementsystem.enums.*;

public class Employee extends User {
    public Employee() {
    }
    public Employee(String id, String name, String username,
                    String password, String email, String phone,
                    int age, Status status) {

        super(id, name, username, password, email, phone,
                age, Role.EMPLOYEE, status);
    }
}
