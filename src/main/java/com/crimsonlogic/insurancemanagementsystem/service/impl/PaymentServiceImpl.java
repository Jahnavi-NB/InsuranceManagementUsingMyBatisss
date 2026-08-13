package com.crimsonlogic.insurancemanagementsystem.service.impl;

import com.crimsonlogic.insurancemanagementsystem.config.MyBatisUtil;
import com.crimsonlogic.insurancemanagementsystem.mapper.*;
import com.crimsonlogic.insurancemanagementsystem.model.*;
import com.crimsonlogic.insurancemanagementsystem.enums.*;
import com.crimsonlogic.insurancemanagementsystem.service.PaymentService;
import com.crimsonlogic.insurancemanagementsystem.validator.PaymentValidator;
import com.crimsonlogic.insurancemanagementsystem.exception.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.apache.ibatis.session.SqlSession;

public class PaymentServiceImpl implements PaymentService {
    public Payment payPremium(String cid, String pid, double amount, PaymentMethod method) throws Exception {
        Payment p = new Payment();
        p.setCustomerId(cid);
        p.setPolicyId(pid);
        p.setAmount(amount);
        p.setMethod(method);
        new PaymentValidator().validate(p);
        try (SqlSession s = MyBatisUtil.openSession()) {
            PolicyMapper pm = s.getMapper(PolicyMapper.class);
            Policy pol = pm.findById(pid);
            if (pol == null) throw new PaymentProcessingException("Policy not found.");
            if (!cid.equals(pol.getCustomerId())) throw new AuthorizationException("Policy does not belong to customer.");
            if (pol.getPremiumStatus() == PremiumStatus.PAID) throw new PaymentProcessingException("Premium already paid.");
            if (Math.abs(amount-pol.getPremium())>.01) throw new PaymentValidationException("Amount must equal premium.");
            p.setId("PAY-" + LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmssSSS")));
            p.setStatus(PaymentStatus.SUCCESS);
            p.setPaidAt(LocalDateTime.now());
            s.getMapper(PaymentMapper.class).insert(p);
            pm.updatePremiumStatus(pid, PremiumStatus.PAID);
            s.commit();
            return p;
        }
    }
    public List<Payment> history(String cid) {
        try (SqlSession s = MyBatisUtil.openSession()) {
            return s.getMapper(PaymentMapper.class).findByCustomer(cid);
        }
    }
}
