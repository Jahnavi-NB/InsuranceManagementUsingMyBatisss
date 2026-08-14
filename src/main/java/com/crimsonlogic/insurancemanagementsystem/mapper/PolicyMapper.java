package com.crimsonlogic.insurancemanagementsystem.mapper;

import java.util.*;
import org.apache.ibatis.annotations.Param;
import com.crimsonlogic.insurancemanagementsystem.model.*;
import com.crimsonlogic.insurancemanagementsystem.enums.*;

public interface PolicyMapper {
    List<Policy> findAll();
    Policy findById(@Param("id") String id);
    List<Policy> findByCustomer(@Param("customerId") String customerId);
    void insert(Policy policy);
    void assignPolicy(@Param("policyId") String policyId,
                      @Param("customerId") String customerId,
                      @Param("agentId") String agentId,
                      @Param("premium") double premium);
    int countAssignedPolicies(@Param("agentId") String agentId);

    int countCustomerPolicy(@Param("customerId") String customerId);
    String findLeastAssignedAgent();
    void updatePremiumStatus(@Param("id") String id,
                             @Param("status") PremiumStatus status);
}
