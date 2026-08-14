package com.crimsonlogic.insurancemanagementsystem.model;

import com.crimsonlogic.insurancemanagementsystem.enums.*;

public class Customer extends User {

    private int age;

    public Customer() {
    }

    public Customer(String id,
                    String name,
                    String username,
                    String password,
                    String email,
                    String phone,
                    int age,
                    Status status) {

        super(id, name, username, password,
                email, phone, Role.CUSTOMER, status);

        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
