package com.crimsonlogic.insurancemanagementsystem.service;

import java.util.*;

import com.crimsonlogic.insurancemanagementsystem.exception.DatabaseOperationException;
import com.crimsonlogic.insurancemanagementsystem.model.*;

public interface PolicyService {
    double calculatePremium(int age, double coverage);
    void createPolicy(Policy policy) throws Exception;
    Policy purchasePolicy(String customerId, String policyId) throws Exception;
    List<Policy> findAll() throws DatabaseOperationException;
    Optional<Policy> findById(String id) throws DatabaseOperationException;
    List<Policy> findByCustomer(String customerId) throws DatabaseOperationException;

    int countAssignedPolicies(String agentId) throws DatabaseOperationException;
}
