package com.example.command;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

import com.example.core.Editor;
import com.example.core.SessionManager;

class CommandFactoryTest {
    
    private CommandFactory factory;
    private Editor mockEditor;
    private SessionManager mockSessionManager;
    
    @BeforeEach
    void setUp() {
        factory = new CommandFactory();
        mockEditor = mock(Editor.class);
        mockSessionManager = mock(SessionManager.class);
    }
    
    @Test
    void testCreateInsertCommand() {
        Command command = factory.createInsertCommand(mockEditor, "div", "test-id", "parent-id", "test content");
        assertNotNull(command);
    }
    
    @Test
    void testCreateAppendCommand() {
        Command command = factory.createAppendCommand(mockEditor, "div", "test-id", "parent-id", "test content");
        assertNotNull(command);
    }
    
    @Test
    void testCreateEditIdCommand() {
        Command command = factory.createEditIdCommand(mockEditor, "old-id", "new-id");
        assertNotNull(command);
    }
    
    @Test
    void testCreateEditTextCommand() {
        Command command = factory.createEditTextCommand(mockEditor, "element-id", "new text");
        assertNotNull(command);
    }
    
    @Test
    void testCreateDeleteCommand() {
        Command command = factory.createDeleteCommand(mockEditor, "element-id");
        assertNotNull(command);
    }
    
    @Test
    void testCreatePrintCommands() {
        assertNotNull(factory.createPrintTreeCommand(mockEditor));
        assertNotNull(factory.createPrintIndentCommand(mockEditor, 2));
        assertNotNull(factory.createSpellCheckCommand(mockEditor));
    }
    
    @Test
    void testCreateDirCommands() {
        assertNotNull(factory.createDirTreeCommand());
        assertNotNull(factory.createDirIndentCommand(2));
    }
    
    @Test
    void testCreateSessionCommands() {
        assertNotNull(factory.createLoadCommand("test.html"));
        assertNotNull(factory.createSaveCommand(mockEditor));
        assertNotNull(factory.createCloseCommand(mockEditor));
        assertNotNull(factory.createShowIdCommand(mockEditor, true));
    }
    
    @Test
    void testNullEditorValidation() {
        assertThrows(IllegalArgumentException.class, () -> 
            factory.createInsertCommand(null, "div", "id", "parent", "text"));
        assertThrows(IllegalArgumentException.class, () -> 
            factory.createSaveCommand(null));
        assertThrows(IllegalArgumentException.class, () -> 
            factory.createPrintTreeCommand(null));
    }
} 