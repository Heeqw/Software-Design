package com.example.command.display;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.core.Editor;
import com.example.core.SessionManager;
import com.example.filesystem.FileSystemNode;
import com.example.spellcheck.SpellChecker;
import com.example.spellcheck.SpellError;

class DisplayCommandsTest {
    
    @Mock private Editor mockEditor;
    @Mock private SessionManager mockSessionManager;
    @Mock private SpellChecker mockSpellChecker;
    @Mock private Document mockDocument;
    @Mock private Element mockHtml;
    @Mock private Element mockHead;
    @Mock private Element mockBody;
    
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // 基本文档结构设置
        when(mockEditor.getDocument()).thenReturn(mockDocument);
        when(mockDocument.root()).thenReturn(mockHtml);
        when(mockDocument.head()).thenReturn(mockHead);
        when(mockDocument.body()).thenReturn(mockBody);
        
        // 模拟元素层级关系
        Elements children = new Elements();
        when(mockHtml.children()).thenReturn(children);
        when(mockHead.children()).thenReturn(new Elements());
        when(mockBody.children()).thenReturn(new Elements());
        
        // 模拟元素属性和文本
        when(mockHtml.tagName()).thenReturn("html");
        when(mockHead.tagName()).thenReturn("head");
        when(mockBody.tagName()).thenReturn("body");
        
        when(mockHtml.ownText()).thenReturn("");
        when(mockHead.ownText()).thenReturn("");
        when(mockBody.ownText()).thenReturn("");
        
        when(mockHtml.hasAttr("id")).thenReturn(true);
        when(mockHtml.attr("id")).thenReturn("html");
        
        // 模拟拼写检查
        when(mockSpellChecker.hasError(any(Element.class))).thenReturn(false);
        when(mockSpellChecker.hasError(anyString())).thenReturn(false);
        when(mockSpellChecker.check(anyString())).thenReturn(Collections.emptyList());
        
        // 设置标准输出捕获
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }
    
    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }
    
    @Test
    void testPrintTreeCommand() {
        when(mockEditor.isShowId()).thenReturn(true);
        
        PrintTreeCommand command = new PrintTreeCommand(mockEditor, mockSpellChecker);
        command.execute();
        
        String output = outputStream.toString();
        assertFalse(output.isEmpty());
        assertTrue(output.contains("<html"));
        assertTrue(output.contains("id=\"html\""));
        assertFalse(command.isReversible());
    }
    
    @Test
    void testPrintIndentCommand() {
        when(mockEditor.isShowId()).thenReturn(true);
        
        PrintIndentCommand command = new PrintIndentCommand(mockEditor, 2, mockSpellChecker);
        command.execute();
        
        String output = outputStream.toString();
        assertFalse(output.isEmpty());
        assertTrue(output.contains("<html"));
        assertTrue(output.contains("</html>"));
        assertFalse(command.isReversible());
    }
    
    @Test
    void testDirTreeCommand() {
        FileSystemNode mockRoot = mock(FileSystemNode.class);
        when(mockRoot.getName()).thenReturn("root");
        when(mockRoot.getChildren()).thenReturn(Collections.emptyList());
        when(mockSessionManager.getFileTree()).thenReturn(mockRoot);
        
        DirTreeCommand command = new DirTreeCommand(mockSessionManager);
        command.execute();
        
        String output = outputStream.toString();
        assertFalse(output.isEmpty());
        assertTrue(output.contains("root"));
    }
    
    @Test
    void testDirIndentCommand() {
        FileSystemNode mockRoot = mock(FileSystemNode.class);
        when(mockRoot.getName()).thenReturn("root");
        when(mockRoot.getChildren()).thenReturn(Collections.emptyList());
        when(mockSessionManager.getFileTree()).thenReturn(mockRoot);
        
        DirIndentCommand command = new DirIndentCommand(mockSessionManager, 2);
        command.execute();
        
        String output = outputStream.toString();
        assertFalse(output.isEmpty());
        assertTrue(output.contains("root"));
    }
    
    @Test
    void testSpellCheckCommand() {
        // 模拟文本节点
        when(mockBody.ownText()).thenReturn("incorrekt text");
        
        List<SpellError> mockErrors = Arrays.asList(
            new SpellError("incorrekt", 0, 9, Arrays.asList("incorrect"))
        );
        
        when(mockSpellChecker.hasError(anyString())).thenReturn(true);
        when(mockSpellChecker.check(anyString())).thenReturn(mockErrors);
        
        SpellCheckCommand command = new SpellCheckCommand(mockEditor, mockSpellChecker);
        command.execute();
        
        String output = outputStream.toString();
        assertTrue(output.contains("Spelling errors found"));
        assertTrue(output.contains("incorrekt"));
        assertTrue(output.contains("incorrect"));
    }
    
    @Test
    void testSpellCheckCommandNoErrors() {
        when(mockSpellChecker.hasError(anyString())).thenReturn(false);
        when(mockSpellChecker.check(anyString())).thenReturn(Collections.emptyList());
        
        SpellCheckCommand command = new SpellCheckCommand(mockEditor, mockSpellChecker);
        command.execute();
        
        String output = outputStream.toString();
        assertTrue(output.contains("No spelling errors found"));
    }
    
    @Test
    void testDisplayCommandsWithNullDocument() {
        when(mockEditor.getDocument()).thenReturn(null);
        
        PrintTreeCommand treeCommand = new PrintTreeCommand(mockEditor, mockSpellChecker);
        PrintIndentCommand indentCommand = new PrintIndentCommand(mockEditor, 2, mockSpellChecker);
        SpellCheckCommand spellCommand = new SpellCheckCommand(mockEditor, mockSpellChecker);
        
        assertDoesNotThrow(() -> treeCommand.execute());
        assertDoesNotThrow(() -> indentCommand.execute());
        assertDoesNotThrow(() -> spellCommand.execute());
    }
} 