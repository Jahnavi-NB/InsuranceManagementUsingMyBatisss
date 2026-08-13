package com.crimsonlogic.insurancemanagementsystem.service.impl;

import com.crimsonlogic.insurancemanagementsystem.config.MyBatisUtil;
import com.crimsonlogic.insurancemanagementsystem.mapper.*;
import com.crimsonlogic.insurancemanagementsystem.model.*;
import com.crimsonlogic.insurancemanagementsystem.enums.*;
import com.crimsonlogic.insurancemanagementsystem.service.ClaimService;
import com.crimsonlogic.insurancemanagementsystem.validator.ClaimValidator;
import com.crimsonlogic.insurancemanagementsystem.exception.*;
import java.util.*;
import org.apache.ibatis.session.SqlSession;

public class ClaimServiceImpl implements ClaimService {


    public Claim submit(Claim c) throws Exception {
        try (SqlSession s = MyBatisUtil.openSession()) {

            Policy p = s.getMapper(PolicyMapper.class)
                    .findById(c.getPolicyId());

            if (p == null || !c.getCustomerId().equals(p.getCustomerId())) {
                throw new AuthorizationException(
                        "Policy does not belong to customer."
                );
            }

            new ClaimValidator().validate(c, p);

            if (s.getMapper(ClaimMapper.class)
                    .findByCustomer(c.getCustomerId())
                    .stream()
                    .anyMatch(x -> x.getStatus() == ClaimStatus.SUBMITTED)) {

                throw new ClaimValidationException(
                        "Customer already has a submitted claim."
                );
            }

            // Generate random Claim ID
            c.setId(generateClaimId());

            c.setAgentId(p.getAgentId());
            c.setStatus(ClaimStatus.SUBMITTED);

            s.getMapper(ClaimMapper.class).insert(c);

            s.commit();

            return c;
        }
    }

    private String generateClaimId() {
        return "CLM-" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();
    }
    private void decide(String id, String agent, ClaimStatus st) throws Exception {
        try (SqlSession s = MyBatisUtil.openSession()) {
            Claim c = s.getMapper(ClaimMapper.class).findById(id);
            if (c == null) throw new ClaimNotFoundException("Claim not found.");
            if (!agent.equals(c.getAgentId())) throw new AuthorizationException("Claim belongs to another agent.");
            if (c.getStatus() != ClaimStatus.SUBMITTED) throw new ClaimProcessingException("Only submitted claims can be processed.");
            s.getMapper(ClaimMapper.class).updateStatus(id, st);
            s.commit();
        }
    }
    public void approve(String id, String agent) throws Exception {
        decide(id, agent, ClaimStatus.APPROVED);
    }
    public void reject(String id, String agent) throws Exception {
        decide(id, agent, ClaimStatus.REJECTED);
    }
    public List<Claim> byAgent(String id) {
        try (SqlSession s = MyBatisUtil.openSession()) {
            return s.getMapper(ClaimMapper.class).findByAgent(id);
        }
    }
    public List<Claim> byCustomer(String id) {
        try (SqlSession s = MyBatisUtil.openSession()) {
            return s.getMapper(ClaimMapper.class).findByCustomer(id);
        }
    }
}
