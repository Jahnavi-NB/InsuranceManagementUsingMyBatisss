package com.crimsonlogic.insurancemanagementsystem.util;

import com.crimsonlogic.insurancemanagementsystem.enums.Role;
import com.crimsonlogic.insurancemanagementsystem.exception.IdGenerationException;
import org.apache.ibatis.session.SqlSession;

import java.util.concurrent.ThreadLocalRandom;

// Utility class responsible for generating application IDs.

public final class IdGenerator {

    private IdGenerator() {
        // Utility class.
    }

    /**
     * Generates a random ID for the given role.
     *
     * @param role    user role
     * @param session MyBatis SQL session
     * @return generated random formatted ID
     */
    public static String generateUserId(
            Role role,
            SqlSession session
    ) {

        if (role == null) {
            throw new IdGenerationException(
                    "Role cannot be null while generating ID."
            );
        }

        String prefix = getPrefix(role);

        // Generate random 6-digit number
        int randomNumber = ThreadLocalRandom.current()
                .nextInt(100000, 1000000);

        return prefix + randomNumber;
    }

    /**
     * Returns the ID prefix for each role.
     */
    private static String getPrefix(Role role) {

        switch (role) {

            case EMPLOYEE:
                return "EMP";

            case AGENT:
                return "AG";

            case CUSTOMER:
                return "CUS";

            case ADMIN:
                return "ADM";

            default:
                throw new IdGenerationException(
                        "Unsupported role: " + role
                );
        }
    }
}