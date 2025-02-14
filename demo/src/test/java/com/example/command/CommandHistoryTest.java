package com.example.command;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.exception.CommandException;

class CommandHistoryTest {
    
    private CommandHistory history;
    private Command mockCommand;
    
    @BeforeEach
    void setUp() {
        history = new CommandHistory();
        mockCommand = mock(Command.class);
        when(mockCommand.isReversible()).thenReturn(true);
    }
    
    @Test
    void testExecuteCommand() {
        history.executeCommand(mockCommand);
        verify(mockCommand).execute();
        assertTrue(history.canUndo());
        assertFalse(history.canRedo());
    }
    
    @Test
    void testExecuteNonReversibleCommand() {
        when(mockCommand.isReversible()).thenReturn(false);
        history.executeCommand(mockCommand);
        verify(mockCommand).execute();
        assertFalse(history.canUndo());
    }
    
    @Test
    void testUndo() {
        history.executeCommand(mockCommand);
        history.undo();
        
        verify(mockCommand).undo();
        assertFalse(history.canUndo());
        assertTrue(history.canRedo());
    }
    
    @Test
    void testUndoWithEmptyStack() {
        assertThrows(CommandException.class, () -> history.undo());
    }
    
    @Test
    void testRedo() {
        history.executeCommand(mockCommand);
        history.undo();
        history.redo();
        
        verify(mockCommand, times(2)).execute();
        assertTrue(history.canUndo());
        assertFalse(history.canRedo());
    }
    
    @Test
    void testRedoWithEmptyStack() {
        assertThrows(CommandException.class, () -> history.redo());
    }
    
    @Test
    void testClear() {
        history.executeCommand(mockCommand);
        history.clear();
        
        assertFalse(history.canUndo());
        assertFalse(history.canRedo());
    }
    
    @Test
    void testExecuteAfterUndo() {
        history.executeCommand(mockCommand);
        history.undo();
        
        Command newCommand = mock(Command.class);
        when(newCommand.isReversible()).thenReturn(true);
        history.executeCommand(newCommand);
        
        assertFalse(history.canRedo());
        assertTrue(history.canUndo());
    }
    
    @Test
    void testCommandExecutionFailure() {
        doThrow(new RuntimeException("Test error")).when(mockCommand).execute();
        
        assertThrows(CommandException.class, () -> history.executeCommand(mockCommand));
    }
    
    @Test
    void testUndoFailure() {
        history.executeCommand(mockCommand);
        doThrow(new RuntimeException("Test error")).when(mockCommand).undo();
        
        assertThrows(CommandException.class, () -> history.undo());
    }
} 