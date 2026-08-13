package com.crimsonlogic.insurancemanagementsystem.exception;

/**
 * Thrown when a user ID cannot be generated.
 */
public class IdGenerationException extends RuntimeException {

    public IdGenerationException(String message) {
        super(message);
    }

    public IdGenerationException(
            String message,
            Throwable cause
    ) {
        super(message, cause);
    }
}