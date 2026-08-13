package com.crimsonlogic.insurancemanagementsystem.model;

import com.crimsonlogic.insurancemanagementsystem.enums.*;

public abstract class User {
    protected String id, name, username, password, email, phone;
    protected Role role;
    protected Status status = Status.ACTIVE;
    public User() {
    }
    public User(String id, String name, String username, String password, String email, String phone, Role role, Status status) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.status = status;
    }
    public String getId() {
        return id;
    }
    public void setId(String v) {
        id = v;
    }
    public String getName() {
        return name;
    }
    public void setName(String v) {
        name = v;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String v) {
        username = v;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String v) {
        password = v;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String v) {
        email = v;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String v) {
        phone = v;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role v) {
        role = v;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status v) {
        status = v;
    }
    public Integer getAge() {
        return null;
    }
}
