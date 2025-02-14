package com.example.exception;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class FileSystemExceptionTest {
    
    @Test
    void testConstructorWithMessageOnly() {
        Path path = Paths.get("/test/path");
        String message = "File not found";
        FileSystemException exception = new FileSystemException(path, message);
        
        assertEquals(path, exception.getPath());
        assertTrue(exception.getMessage().contains(path.toString()));
        assertTrue(exception.getMessage().contains(message));
    }
    
    @Test
    void testConstructorWithMessageAndCause() {
        Path path = Paths.get("/test/path");
        String message = "File not found";
        Throwable cause = new RuntimeException("Original error");
        
        FileSystemException exception = new FileSystemException(path, message, cause);
        
        assertEquals(path, exception.getPath());
        assertTrue(exception.getMessage().contains(path.toString()));
        assertTrue(exception.getMessage().contains(message));
        assertEquals(cause, exception.getCause());
    }
} 