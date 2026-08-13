package com.crimsonlogic.insurancemanagementsystem.mapper;

import java.util.*;
import com.crimsonlogic.insurancemanagementsystem.model.*;
import com.crimsonlogic.insurancemanagementsystem.enums.*;

public interface PaymentMapper {
    void insert(Payment payment);
    List<Payment> findByCustomer(String customerId);
}
