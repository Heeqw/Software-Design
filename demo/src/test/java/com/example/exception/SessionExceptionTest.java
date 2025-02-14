package com.example.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class SessionExceptionTest {
    
    @Test
    void testConstructorWithMessage() {
        String message = "Session error";
        SessionException exception = new SessionException(message);
        
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }
    
    @Test
    void testConstructorWithMessageAndCause() {
        String message = "Session error";
        Throwable cause = new RuntimeException("Original error");
        SessionException exception = new SessionException(message, cause);
        
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
} 