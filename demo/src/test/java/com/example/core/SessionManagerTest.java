package com.example.core;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class SessionManagerTest {
    
    @TempDir
    Path tempDir;
    
    private SessionManager sessionManager;
    private Path testHtmlFile;
    
    @BeforeEach
    void setUp() throws Exception {
        // 创建测试HTML文件
        testHtmlFile = tempDir.resolve("test.html");
        Files.writeString(testHtmlFile, "<html><head><title>Test</title></head><body>Test content</body></html>");
        
        // 获取SessionManager实例
        sessionManager = SessionManager.getInstance();
    }
    
    @Test
    void testLoadFile() {
        Editor editor = sessionManager.loadFile(testHtmlFile.toString());
        assertNotNull(editor);
        assertEquals(testHtmlFile.toAbsolutePath().normalize().toString(), editor.getFilePath());
    }
    
    @Test
    void testLoadNonHtmlFile() {
        assertThrows(IllegalArgumentException.class, () -> 
            sessionManager.loadFile("test.txt")
        );
    }
    
    @Test
    void testLoadAlreadyOpenFile() {
        sessionManager.loadFile(testHtmlFile.toString());
        assertThrows(IllegalStateException.class, () -> 
            sessionManager.loadFile(testHtmlFile.toString())
        );
    }
    
    @Test
    void testCloseEditor() {
        Editor editor = sessionManager.loadFile(testHtmlFile.toString());
        assertTrue(sessionManager.closeEditor(editor));
        assertNull(sessionManager.getActiveEditor());
    }
    
    @Test
    void testGetModifiedEditors() throws Exception {
        Editor editor = sessionManager.loadFile(testHtmlFile.toString());
        editor.getDocument().body().text("Modified content");
        editor.markModified();
        
        List<Editor> modifiedEditors = sessionManager.getModifiedEditors();
        assertEquals(1, modifiedEditors.size());
        assertTrue(modifiedEditors.contains(editor));
    }
    
    @Test
    void testIsFileModified() {
        Editor editor = sessionManager.loadFile(testHtmlFile.toString());
        editor.markModified();
        
        assertTrue(sessionManager.isFileModified(testHtmlFile.toString()));
        assertFalse(sessionManager.isFileModified("nonexistent.html"));
    }
    
    @Test
    void testGetOpenFiles() {
        sessionManager.loadFile(testHtmlFile.toString());
        List<String> openFiles = sessionManager.getOpenFiles();
        
        assertEquals(1, openFiles.size());
        assertTrue(openFiles.contains(testHtmlFile.toAbsolutePath().normalize().toString()));
    }
    
    @Test
    void testIsFileOpen() {
        sessionManager.loadFile(testHtmlFile.toString());
        assertTrue(sessionManager.isFileOpen(testHtmlFile.toAbsolutePath().normalize().toString()));
        assertFalse(sessionManager.isFileOpen("nonexistent.html"));
    }
    
    @Test
    void testGetEditorByPath() {
        Editor editor = sessionManager.loadFile(testHtmlFile.toString());
        assertSame(editor, sessionManager.getEditorByPath(testHtmlFile.toString()));
        assertNull(sessionManager.getEditorByPath("nonexistent.html"));
    }
    
    @Test
    void testFileTreeCache() {
        assertNotNull(sessionManager.getFileTree());
        sessionManager.invalidateFileTreeCache();
        assertNotNull(sessionManager.getFileTree());
    }
} 