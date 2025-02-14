package com.example.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class DocumentExceptionTest {
    
    @Test
    void testConstructorWithMessageOnly() {
        String elementId = "test-id";
        String message = "Invalid element";
        DocumentException exception = new DocumentException(elementId, message);
        
        assertEquals(elementId, exception.getElementId());
        assertTrue(exception.getMessage().contains(elementId));
        assertTrue(exception.getMessage().contains(message));
    }
    
    @Test
    void testConstructorWithMessageAndCause() {
        String elementId = "test-id";
        String message = "Invalid element";
        Throwable cause = new RuntimeException("Original error");
        
        DocumentException exception = new DocumentException(elementId, message, cause);
        
        assertEquals(elementId, exception.getElementId());
        assertTrue(exception.getMessage().contains(elementId));
        assertTrue(exception.getMessage().contains(message));
        assertEquals(cause, exception.getCause());
    }
} 