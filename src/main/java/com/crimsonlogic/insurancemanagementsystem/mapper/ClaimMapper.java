package com.crimsonlogic.insurancemanagementsystem.mapper;

import java.util.*;
import org.apache.ibatis.annotations.Param;
import com.crimsonlogic.insurancemanagementsystem.model.*;
import com.crimsonlogic.insurancemanagementsystem.enums.*;

public interface ClaimMapper {
    void insert(Claim claim);
    Claim findById(@Param("id") String id);
    List<Claim> findByAgent(@Param("agentId") String agentId);
    List<Claim> findByCustomer(@Param("customerId") String customerId);
    void updateStatus(@Param("id") String id, @Param("status") ClaimStatus status);
}
