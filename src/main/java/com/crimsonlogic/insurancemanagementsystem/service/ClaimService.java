package com.crimsonlogic.insurancemanagementsystem.service;

import java.util.*;
import com.crimsonlogic.insurancemanagementsystem.model.*;
import com.crimsonlogic.insurancemanagementsystem.enums.*;

public interface ClaimService {
    Claim submit(Claim claim) throws Exception;
    void approve(String claimId, String agentId) throws Exception;
    void reject(String claimId, String agentId) throws Exception;
    List<Claim> byAgent(String agentId);
    List<Claim> byCustomer(String customerId);
}
