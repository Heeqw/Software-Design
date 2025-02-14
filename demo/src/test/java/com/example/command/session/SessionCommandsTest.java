package com.example.command.session;

import java.nio.file.Path;

import org.jsoup.nodes.Document;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.core.Editor;
import com.example.core.SessionManager;
import com.example.exception.CommandException;

class SessionCommandsTest {
    
    @TempDir
    Path tempDir;
    
    @Mock private SessionManager mockSessionManager;
    @Mock private Editor mockEditor;
    @Mock private Document mockDocument;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // 基本设置
        when(mockEditor.getDocument()).thenReturn(mockDocument);
        when(mockDocument.html()).thenReturn("<html><body>test</body></html>");
        
        // 模拟文件路径
        String testPath = tempDir.resolve("test.html").toString();
        when(mockEditor.getFilePath()).thenReturn(testPath);
        
        // 模拟会话管理器行为
        when(mockSessionManager.loadFile(anyString())).thenReturn(mockEditor);
        when(mockSessionManager.closeEditor(any(Editor.class))).thenReturn(true);
        when(mockSessionManager.getActiveEditor()).thenReturn(mockEditor);
    }
    
    @Test
    void testLoadCommand() {
        // 确保文件未打开
        when(mockSessionManager.isFileOpen(anyString())).thenReturn(false);
        
        Path testFile = tempDir.resolve("test.html");
        LoadCommand command = new LoadCommand(mockSessionManager, testFile.toString());
        
        // 执行加载
        assertDoesNotThrow(() -> command.execute());
        verify(mockSessionManager).loadFile(anyString());
        
        // 测试撤销
        assertDoesNotThrow(() -> command.undo());
        verify(mockSessionManager).closeEditor(mockEditor);
    }
    
    @Test
    void testSaveCommand() {
        // 模拟文件已修改
        when(mockEditor.isModified()).thenReturn(true);
        
        SaveCommand command = new SaveCommand(mockEditor);
        
        // 执行保存
        assertDoesNotThrow(() -> command.execute());
        verify(mockEditor).save();
        
        // 测试撤销（恢复备份）
        assertDoesNotThrow(() -> command.undo());
        verify(mockEditor).reloadDocument();
    }
    
    @Test
    void testCloseCommand() {
        // 模拟编辑器状态
        when(mockEditor.isModified()).thenReturn(true);
        when(mockSessionManager.getActiveEditor()).thenReturn(mockEditor);
        
        CloseCommand command = new CloseCommand(mockSessionManager, mockEditor);
        
        // 执行关闭
        assertDoesNotThrow(() -> command.execute());
        verify(mockEditor).resetModifiedState();
        verify(mockSessionManager).closeEditor(mockEditor);
        
        // 测试撤销（重新加载）
        when(mockSessionManager.loadFile(anyString())).thenReturn(mockEditor);
        assertDoesNotThrow(() -> command.undo());
        verify(mockSessionManager).loadFile(anyString());
    }
    
    @Test
    void testShowIdCommand() {
        // 模拟当前显示状态
        when(mockEditor.isShowId()).thenReturn(true);
        
        ShowIdCommand command = new ShowIdCommand(mockEditor, false);
        
        // 执行命令
        command.execute();
        verify(mockEditor).setShowId(false);
        
        // 测试撤销
        command.undo();
        verify(mockEditor).setShowId(true);
    }
    
    @Test
    void testLoadCommandWithExistingFile() {
        // 模拟文件已打开
        when(mockSessionManager.isFileOpen(anyString())).thenReturn(true);
        
        LoadCommand command = new LoadCommand(mockSessionManager, "test.html");
        assertThrows(CommandException.class, () -> command.execute());
    }
    
    @Test
    void testCloseCommandWithUnsavedChanges() {
        // 模拟未保存的更改
        when(mockEditor.isModified()).thenReturn(true);
        when(mockDocument.html()).thenReturn("<html><body>modified</body></html>");
        
        CloseCommand command = new CloseCommand(mockSessionManager, mockEditor);
        
        // 执行关闭
        assertDoesNotThrow(() -> command.execute());
        verify(mockEditor).resetModifiedState();
        verify(mockSessionManager).closeEditor(mockEditor);
        
        // 测试撤销（应该恢复修改的内容）
        when(mockSessionManager.loadFile(anyString())).thenReturn(mockEditor);
        assertDoesNotThrow(() -> command.undo());
        verify(mockDocument).html(anyString());
    }
    
    @Test
    void testSaveCommandWithUnmodifiedFile() {
        // 模拟文件未修改
        when(mockEditor.isModified()).thenReturn(false);
        
        SaveCommand command = new SaveCommand(mockEditor);
        
        // 执行保存
        assertDoesNotThrow(() -> command.execute());
        verify(mockEditor, never()).save();  // 未修改的文件不应该保存
    }
    
    @Test
    void testCloseCommandWithActiveEditor() {
        // 模拟当前是活动编辑器
        when(mockSessionManager.getActiveEditor()).thenReturn(mockEditor);
        when(mockEditor.isModified()).thenReturn(false);
        
        CloseCommand command = new CloseCommand(mockSessionManager, mockEditor);
        
        // 执行关闭
        assertDoesNotThrow(() -> command.execute());
        verify(mockSessionManager).closeEditor(mockEditor);
        
        // 测试撤销（应该恢复活动状态）
        when(mockSessionManager.loadFile(anyString())).thenReturn(mockEditor);
        assertDoesNotThrow(() -> command.undo());
        verify(mockSessionManager).setActiveEditor(mockEditor);
    }
} 