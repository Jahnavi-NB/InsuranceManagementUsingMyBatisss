package com.crimsonlogic.insurancemanagementsystem.service.impl;

import com.crimsonlogic.insurancemanagementsystem.config.MyBatisUtil;
import com.crimsonlogic.insurancemanagementsystem.enums.Role;
import com.crimsonlogic.insurancemanagementsystem.enums.Status;
import com.crimsonlogic.insurancemanagementsystem.exception.DatabaseOperationException;
import com.crimsonlogic.insurancemanagementsystem.exception.DuplicateUserException;
import com.crimsonlogic.insurancemanagementsystem.mapper.UserMapper;
import com.crimsonlogic.insurancemanagementsystem.model.Agent;
import com.crimsonlogic.insurancemanagementsystem.model.Customer;
import com.crimsonlogic.insurancemanagementsystem.model.Employee;
import com.crimsonlogic.insurancemanagementsystem.model.User;
import com.crimsonlogic.insurancemanagementsystem.service.UserService;
import com.crimsonlogic.insurancemanagementsystem.util.IdGenerator;
import com.crimsonlogic.insurancemanagementsystem.validator.AgentValidator;
import com.crimsonlogic.insurancemanagementsystem.validator.CustomerValidator;
import com.crimsonlogic.insurancemanagementsystem.validator.EmployeeValidator;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Implementation of user-related operations.
 *
 * Handles:
 * - Employee registration
 * - Agent registration
 * - Customer registration
 * - User status changes
 * - Viewing users
 * - Role-based ID generation
 */
public class UserServiceImpl implements UserService {

    /**
     * Saves a new user in the database.
     *
     * The ID is generated according to the user's role:
     *
     * EMPLOYEE -> EMP001
     * AGENT    -> AG001
     * CUSTOMER -> CUS001
     */
    private void save(User user, Role role) throws Exception {

        try (SqlSession session = MyBatisUtil.openSession()) {

            UserMapper mapper =
                    session.getMapper(UserMapper.class);

            /*
             * Check whether the username already exists.
             */
            if (mapper.countByUsername(user.getUsername()) > 0) {

                throw new DuplicateUserException(
                        "Username already exists."
                );
            }

            /*
             * Generate role-based ID.
             *
             * Employee -> EMP001
             * Agent    -> AG001
             * Customer -> CUS001
             */
            String generatedId =
                    IdGenerator.generateUserId(
                            role,
                            session
                    );

            user.setId(generatedId);

            /*
             * Set role explicitly.
             */
            user.setRole(role);

            /*
             * Newly registered users are ACTIVE.
             */
            user.setStatus(Status.ACTIVE);

            /*
             * Insert user into database.
             */
            mapper.insertUser(user);

            /*
             * Commit transaction.
             */
            session.commit();

        } catch (DuplicateUserException e) {

            /*
             * Re-throw the validation/business exception.
             */
            throw e;

        } catch (Exception e) {

            /*
             * Convert database errors into
             * the application's custom exception.
             */
            throw new DatabaseOperationException(
                    "Unable to save user: "
                            + e.getMessage()
            );
        }
    }

    /**
     * Registers a new employee.
     */
    @Override
    public void registerEmployee(Employee employee)
            throws Exception {

        /*
         * Employee-specific validation.
         */
        new EmployeeValidator().validate(employee);

        /*
         * Save with EMP role.
         */
        save(employee, Role.EMPLOYEE);
    }

    /**
     * Registers a new agent.
     */
    @Override
    public void registerAgent(Agent agent)
            throws Exception {

        /*
         * Agent-specific validation.
         */
        new AgentValidator().validate(agent);

        /*
         * Save with AGENT role.
         */
        save(agent, Role.AGENT);
    }

    /**
     * Registers a new customer.
     */
    @Override
    public void registerCustomer(Customer customer)
            throws Exception {

        /*
         * Customer-specific validation.
         */
        new CustomerValidator().validate(customer);

        /*
         * Save with CUSTOMER role.
         */
        save(customer, Role.CUSTOMER);
    }

    /**
     * Changes the status of a user.
     *
     * Instead of deleting users, the application
     * changes their status to ACTIVE or INACTIVE.
     */
    @Override
    public void changeStatus(
            String id,
            Status status
    ) throws Exception {

        try (SqlSession session =
                     MyBatisUtil.openSession()) {

            UserMapper mapper =
                    session.getMapper(UserMapper.class);

            /*
             * Update user status.
             */
            mapper.updateStatus(
                    id,
                    status
            );

            /*
             * Commit transaction.
             */
            session.commit();

        } catch (Exception e) {

            throw new DatabaseOperationException(
                    "Unable to change user status: "
                            + e.getMessage()
            );
        }
    }

    /**
     * Returns all users.
     */
    @Override
    public List<User> findAll() throws DatabaseOperationException {

        try (SqlSession session =
                     MyBatisUtil.openSession()) {

            UserMapper mapper =
                    session.getMapper(UserMapper.class);

            return mapper.findAll();

        } catch (Exception e) {

            throw new DatabaseOperationException(
                    "Unable to retrieve users: "
                            + e.getMessage()
            );
        }
    }
}