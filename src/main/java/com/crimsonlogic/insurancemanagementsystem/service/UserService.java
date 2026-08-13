package com.crimsonlogic.insurancemanagementsystem.service;

import java.util.*;

import com.crimsonlogic.insurancemanagementsystem.exception.DatabaseOperationException;
import com.crimsonlogic.insurancemanagementsystem.model.*;
import com.crimsonlogic.insurancemanagementsystem.enums.*;

public interface UserService {
    void registerEmployee(Employee employee) throws Exception;
    void registerAgent(Agent agent) throws Exception;
    void registerCustomer(Customer customer) throws Exception;
    void changeStatus(String id, Status status) throws Exception;
    List<User> findAll() throws DatabaseOperationException;
}
