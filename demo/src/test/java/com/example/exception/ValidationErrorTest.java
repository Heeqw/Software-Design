package com.example.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ValidationErrorTest {
    
    @Test
    void testValidationError() {
        String field = "username";
        String message = "Username is required";
        ValidationError error = new ValidationError(field, message);
        
        assertEquals(field, error.getField());
        assertEquals(message, error.getMessage());
    }
} 