package com.example.core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.command.Command;
import com.example.filesystem.HtmlFileNode;

class EditorTest {
    
    private Editor editor;
    private HtmlFileNode mockFileNode;
    private Document testDocument;
    
    @BeforeEach
    void setUp() {
        mockFileNode = mock(HtmlFileNode.class);
        testDocument = Jsoup.parse("<html><head><title>Test</title></head><body>Initial content</body></html>");
        when(mockFileNode.getHtmlDocument()).thenReturn(testDocument);
        when(mockFileNode.getPath()).thenReturn("/test/path.html");
        
        editor = new Editor(mockFileNode);
    }
    
    @Test
    void testInitialState() {
        assertFalse(editor.isModified());
        assertTrue(editor.isShowId());
        assertEquals("/test/path.html", editor.getFilePath());
        assertNotNull(editor.getDocument());
    }
    
    @Test
    void testExecuteCommand() {
        Command mockCommand = mock(Command.class);
        editor.executeCommand(mockCommand);
        verify(mockCommand).execute();
    }
    
    @Test
    void testUndo() {
        Command mockCommand = mock(Command.class);
        editor.executeCommand(mockCommand);
        editor.undo();
        verify(mockCommand).undo();
    }
    
    @Test
    void testRedo() {
        Command mockCommand = mock(Command.class);
        editor.executeCommand(mockCommand);
        editor.undo();
        editor.redo();
        verify(mockCommand, times(2)).execute();
    }
    
    @Test
    void testModificationState() {
        // 修改文档内容
        testDocument.body().text("Modified content");
        assertTrue(editor.isModified());
        
        // 保存后应该重置修改状态
        editor.save();
        assertFalse(editor.isModified());
    }
    
    @Test
    void testSave() {
        editor.save();
        verify(mockFileNode).saveDocument();
        assertFalse(editor.isModified());
    }
    
    @Test
    void testShowIdToggle() {
        assertTrue(editor.isShowId());
        editor.setShowId(false);
        assertFalse(editor.isShowId());
    }
    
    @Test
    void testReloadDocument() {
        editor.reloadDocument();
        verify(mockFileNode).reloadDocument();
        assertFalse(editor.isModified());
    }
} 