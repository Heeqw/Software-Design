package com.example.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class CommandExceptionTest {
    
    @Test
    void testConstructorWithMessageOnly() {
        String commandName = "test-command";
        String message = "Command failed";
        CommandException exception = new CommandException(commandName, message);
        
        assertEquals(commandName, exception.getCommandName());
        assertTrue(exception.getMessage().contains(commandName));
        assertTrue(exception.getMessage().contains(message));
    }
    
    @Test
    void testConstructorWithMessageAndCause() {
        String commandName = "test-command";
        String message = "Command failed";
        Throwable cause = new RuntimeException("Original error");
        
        CommandException exception = new CommandException(commandName, message, cause);
        
        assertEquals(commandName, exception.getCommandName());
        assertTrue(exception.getMessage().contains(commandName));
        assertTrue(exception.getMessage().contains(message));
        assertEquals(cause, exception.getCause());
    }
} 