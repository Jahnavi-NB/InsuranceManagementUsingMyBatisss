package com.crimsonlogic.insurancemanagementsystem.mapper;

import com.crimsonlogic.insurancemanagementsystem.enums.Status;
import com.crimsonlogic.insurancemanagementsystem.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    User findByUsername(String username, String password);

    User findById(String id);

    List<User> findAll();

    void insertUser(User user);

    int countByUsername(String username);

    void updateStatus(
            @Param("id") String id,
            @Param("status") Status status
    );

    /**
     * Finds the highest numeric portion of the
     * existing IDs for a particular role.
     *
     * Example:
     * EMP001
     * EMP002
     * EMP005
     *
     * Returns:
     * 5
     */
    Integer findLastUserNumber(String prefix);

    /**
     * Finds all active agents.
     *
     * Used when a customer purchases a policy.
     * The PolicyService automatically selects the
     * least-loaded active agent.
     */
    List<User> findActiveAgents();
}