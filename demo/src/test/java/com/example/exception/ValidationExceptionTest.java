package com.example.exception;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ValidationExceptionTest {
    
    @Test
    void testConstructorWithMessage() {
        String message = "Validation failed";
        ValidationException exception = new ValidationException(message);
        
        assertEquals(message, exception.getMessage());
        assertTrue(exception.getErrors().isEmpty());
    }
    
    @Test
    void testConstructorWithErrors() {
        List<ValidationError> errors = Arrays.asList(
            new ValidationError("field1", "Error 1"),
            new ValidationError("field2", "Error 2")
        );
        
        ValidationException exception = new ValidationException(errors);
        
        assertEquals(errors, exception.getErrors());
        assertTrue(exception.getMessage().contains("Error 1"));
        assertTrue(exception.getMessage().contains("Error 2"));
    }
    
    @Test
    void testErrorFormatting() {
        List<ValidationError> errors = Arrays.asList(
            new ValidationError("username", "Username is required"),
            new ValidationError("email", "Invalid email format")
        );
        
        ValidationException exception = new ValidationException(errors);
        String message = exception.getMessage();
        
        assertTrue(message.contains("Username is required"));
        assertTrue(message.contains("Invalid email format"));
        assertTrue(message.contains("\n-")); // 检查格式化的分隔符
    }
} 