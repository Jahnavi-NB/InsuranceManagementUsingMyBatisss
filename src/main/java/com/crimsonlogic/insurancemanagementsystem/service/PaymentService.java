package com.crimsonlogic.insurancemanagementsystem.service;

import java.util.*;
import com.crimsonlogic.insurancemanagementsystem.model.*;
import com.crimsonlogic.insurancemanagementsystem.enums.*;

public interface PaymentService {
    Payment payPremium(String customerId, String policyId, double amount, PaymentMethod method) throws Exception;
    List<Payment> history(String customerId);
}
