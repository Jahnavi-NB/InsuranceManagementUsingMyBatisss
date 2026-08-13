package com.crimsonlogic.insurancemanagementsystem.util;

import com.crimsonlogic.insurancemanagementsystem.enums.Role;
import com.crimsonlogic.insurancemanagementsystem.exception.IdGenerationException;
import com.crimsonlogic.insurancemanagementsystem.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.Optional;

/**
 * Utility class responsible for generating application IDs.
 *
 * Employee  -> EMP001, EMP002, EMP003...
 * Agent     -> AG001, AG002, AG003...
 * Customer  -> CUS001, CUS002, CUS003...
 * Admin     -> ADM001, ADM002...
 */
public final class IdGenerator {

    private IdGenerator() {
        // Utility class.
    }

    /**
     * Generates the next ID for the given role.
     *
     * @param role    user role
     * @param session MyBatis SQL session
     * @return generated formatted ID
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

        UserMapper userMapper =
                session.getMapper(UserMapper.class);

        Integer lastNumber =
                userMapper.findLastUserNumber(prefix);

        int nextNumber =
                Optional.ofNullable(lastNumber)
                        .orElse(0) + 1;

        return String.format(
                "%s%03d",
                prefix,
                nextNumber
        );
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