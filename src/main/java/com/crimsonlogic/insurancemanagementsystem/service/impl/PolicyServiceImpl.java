package com.crimsonlogic.insurancemanagementsystem.service.impl;

import com.crimsonlogic.insurancemanagementsystem.config.MyBatisUtil;
import com.crimsonlogic.insurancemanagementsystem.enums.PremiumStatus;
import com.crimsonlogic.insurancemanagementsystem.enums.Role;
import com.crimsonlogic.insurancemanagementsystem.enums.Status;
import com.crimsonlogic.insurancemanagementsystem.exception.DatabaseOperationException;
import com.crimsonlogic.insurancemanagementsystem.exception.PolicyAlreadyPurchasedException;
import com.crimsonlogic.insurancemanagementsystem.exception.PolicyNotFoundException;
import com.crimsonlogic.insurancemanagementsystem.exception.PolicyProcessingException;
import com.crimsonlogic.insurancemanagementsystem.exception.UserNotFoundException;
import com.crimsonlogic.insurancemanagementsystem.mapper.PolicyMapper;
import com.crimsonlogic.insurancemanagementsystem.mapper.UserMapper;
import com.crimsonlogic.insurancemanagementsystem.model.Customer;
import com.crimsonlogic.insurancemanagementsystem.model.Policy;
import com.crimsonlogic.insurancemanagementsystem.model.User;
import com.crimsonlogic.insurancemanagementsystem.service.PolicyService;
import com.crimsonlogic.insurancemanagementsystem.validator.PolicyValidator;
import org.apache.ibatis.session.SqlSession;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of policy-related operations.
 *
 * Responsibilities:
 * - Create policies
 * - Calculate premium based on age
 * - Purchase policies
 * - Automatically assign an agent
 * - Retrieve policies
 */
public class PolicyServiceImpl implements PolicyService {

    private final PolicyValidator validator =
            new PolicyValidator();

    /**
     * Calculates premium based on customer's age
     * and policy coverage amount.
     *
     * Premium rates:
     *
     * Age below 30  -> 2%
     * Age 30-44     -> 3%
     * Age 45-59     -> 4.5%
     * Age 60 or more -> 6%
     *
     * @param age customer age
     * @param coverage policy coverage amount
     * @return calculated premium
     */
    @Override
    public double calculatePremium(
            int age,
            double coverage
    ) {

        double rate;

        if (age < 30) {

            rate = 0.02;

        } else if (age < 45) {

            rate = 0.03;

        } else if (age < 60) {

            rate = 0.045;

        } else {

            rate = 0.06;
        }

        double premium = coverage * rate;

        /*
         * Round premium to two decimal places.
         */
        return Math.round(premium * 100) / 100.0;
    }

    /**
     * Creates a new policy.
     */
    @Override
    public void createPolicy(Policy policy)
            throws Exception {

        /*
         * Validate policy data before saving.
         */
        validator.validate(policy);

        try (SqlSession session =
                     MyBatisUtil.openSession()) {

            PolicyMapper mapper =
                    session.getMapper(PolicyMapper.class);

            mapper.insert(policy);

            session.commit();

        } catch (Exception e) {

            throw new DatabaseOperationException(
                    "Unable to create policy: "
                            + e.getMessage()
            );
        }
    }

    /**
     * Purchases a policy for a customer.
     *
     * The system automatically assigns the active
     * agent with the lowest number of assigned policies.
     */
    @Override
    public Policy purchasePolicy(
            String customerId,
            String policyId
    ) throws Exception {

        try (SqlSession session =
                     MyBatisUtil.openSession()) {

            UserMapper userMapper =
                    session.getMapper(UserMapper.class);

            PolicyMapper policyMapper =
                    session.getMapper(PolicyMapper.class);

            /*
             * Find the customer.
             */
            User user =
                    userMapper.findById(customerId);

            /*
             * Validate customer.
             */
            if (user == null
                    || user.getRole() != Role.CUSTOMER
                    || user.getStatus() != Status.ACTIVE) {

                throw new UserNotFoundException(
                        "Active customer not found."
                );
            }

            /*
             * Make sure the returned user is actually
             * a Customer object before accessing age.
             */
            if (!(user instanceof Customer)) {

                throw new UserNotFoundException(
                        "Customer information is invalid."
                );
            }

            Customer customer =
                    (Customer) user;

            /*
             * Find the policy.
             */
            Policy policy =
                    policyMapper.findById(policyId);

            if (policy == null) {

                throw new PolicyNotFoundException(
                        "Policy not found."
                );
            }

            /*
             * Prevent purchasing an already purchased policy.
             */
            if (policy.getCustomerId() != null
                    && !policy.getCustomerId().isBlank()) {

                throw new PolicyAlreadyPurchasedException(
                        "This policy has already been purchased."
                );
            }

            /*
             * Find all active agents.
             */
            List<User> agents =
                    userMapper.findActiveAgents();

            if (agents == null || agents.isEmpty()) {

                throw new PolicyProcessingException(
                        "No active agents are available."
                );
            }

            /*
             * Store the number of policies assigned
             * to each agent.
             *
             * HashMap is used as required.
             */
            Map<String, Integer> agentLoad =
                    new HashMap<>();

            for (User agent : agents) {

                int assignedPolicies =
                        policyMapper.countAssignedPolicies(
                                agent.getId()
                        );

                agentLoad.put(
                        agent.getId(),
                        assignedPolicies
                );
            }

            /*
             * Select the least-loaded agent.
             *
             * If two agents have the same number
             * of policies, the agent name is used
             * as the secondary comparison.
             */
            User selectedAgent =
                    agents.stream()
                            .min(
                                    Comparator
                                            .comparingInt(
                                                    (User agent) ->
                                                            agentLoad.get(
                                                                    agent.getId()
                                                            )
                                            )
                                            .thenComparing(
                                                    User::getName
                                            )
                            )
                            .orElseThrow(
                                    () ->
                                            new PolicyProcessingException(
                                                    "Unable to assign an agent."
                                            )
                            );

            /*
             * Calculate premium using customer's age
             * and the policy coverage amount.
             */
            double calculatedPremium =
                    calculatePremium(
                            customer.getAge(),
                            policy.getCoverageAmount()
                    );

            /*
             * Set policy information in the object.
             */
            policy.setCustomerId(customerId);
            policy.setAgentId(selectedAgent.getId());
            policy.setPremiumStatus(PremiumStatus.DUE);
            policy.setStatus(Status.ACTIVE);

            /*
             * Save customer, agent and premium information.
             */
            policyMapper.assignPolicy(
                    policy.getId(),
                    customerId,
                    selectedAgent.getId(),
                    calculatedPremium
            );

            /*
             * Commit transaction.
             */
            session.commit();

            /*
             * Return the updated policy.
             */
            return policyMapper.findById(policyId);

        } catch (
                PolicyAlreadyPurchasedException
                | PolicyNotFoundException
                | UserNotFoundException
                | PolicyProcessingException e
        ) {

            /*
             * Business exceptions are re-thrown
             * without changing their messages.
             */
            throw e;

        } catch (Exception e) {

            /*
             * Database or unexpected errors.
             */
            throw new DatabaseOperationException(
                    "Unable to purchase policy: "
                            + e.getMessage()
            );
        }
    }

    /**
     * Returns all policies.
     */
    @Override
    public List<Policy> findAll() throws DatabaseOperationException {

        try (SqlSession session =
                     MyBatisUtil.openSession()) {

            PolicyMapper mapper =
                    session.getMapper(PolicyMapper.class);

            return mapper.findAll();

        } catch (Exception e) {

            throw new DatabaseOperationException(
                    "Unable to retrieve policies: "
                            + e.getMessage()
            );
        }
    }

    /**
     * Finds a policy using its ID.
     *
     * Optional is used so that null values are
     * handled safely.
     */
    @Override
    public Optional<Policy> findById(String id) throws DatabaseOperationException {

        try (SqlSession session =
                     MyBatisUtil.openSession()) {

            PolicyMapper mapper =
                    session.getMapper(PolicyMapper.class);

            return Optional.ofNullable(
                    mapper.findById(id)
            );

        } catch (Exception e) {

            throw new DatabaseOperationException(
                    "Unable to find policy: "
                            + e.getMessage()
            );
        }
    }

    /**
     * Finds all policies belonging to a customer.
     */
    @Override
    public List<Policy> findByCustomer(String customerId) throws DatabaseOperationException {

        try (SqlSession session =
                     MyBatisUtil.openSession()) {

            PolicyMapper mapper =
                    session.getMapper(PolicyMapper.class);

            return mapper.findByCustomer(customerId);

        } catch (Exception e) {

            throw new DatabaseOperationException(
                    "Unable to retrieve customer policies: "
                            + e.getMessage()
            );
        }
    }

    @Override
    public int countAssignedPolicies(String agentId)
            throws DatabaseOperationException {

        try (SqlSession session =
                     MyBatisUtil.openSession()) {

            PolicyMapper mapper =
                    session.getMapper(PolicyMapper.class);

            return mapper.countAssignedPolicies(agentId);

        } catch (Exception e) {

            throw new DatabaseOperationException(
                    "Unable to count assigned policies: "
                            + e.getMessage()
            );
        }
    }
}