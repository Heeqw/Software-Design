package com.example.display.formatter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.core.Editor;
import com.example.spellcheck.SpellChecker;

class DisplayFormatterTest {
    
    private DisplayFormatter formatter;
    private Editor mockEditor;
    private SpellChecker mockSpellChecker;
    private Document testDocument;
    
    @BeforeEach
    void setUp() {
        mockEditor = mock(Editor.class);
        mockSpellChecker = mock(SpellChecker.class);
        formatter = new DisplayFormatter(mockEditor, mockSpellChecker);
        
        testDocument = Jsoup.parse(
            "<html id='root'><head id='head'><title>Test</title></head>" +
            "<body id='body'>Content</body></html>"
        );
        when(mockEditor.getDocument()).thenReturn(testDocument);
    }
    
    @Test
    void testFormatAsTree() {
        when(mockEditor.isShowId()).thenReturn(true);
        when(mockSpellChecker.hasError(any(String.class))).thenReturn(false);
        
        String result = formatter.formatAsTree();
        
        assertNotNull(result);
        assertTrue(result.contains("└── <html id=\"root\">"));
        assertTrue(result.contains("    └── <head id=\"head\">"));
        assertTrue(result.contains("        └── <title>"));
        assertTrue(result.contains("    └── <body id=\"body\">"));
    }
    
    @Test
    void testFormatAsTreeWithoutId() {
        when(mockEditor.isShowId()).thenReturn(false);
        when(mockSpellChecker.hasError(any(String.class))).thenReturn(false);
        
        String result = formatter.formatAsTree();
        
        assertNotNull(result);
        assertTrue(result.contains("└── <html>"));
        assertTrue(result.contains("    └── <head>"));
        assertFalse(result.contains("id=\""));
    }
    
    @Test
    void testFormatAsIndent() {
        when(mockEditor.isShowId()).thenReturn(true);
        when(mockSpellChecker.hasError(any(String.class))).thenReturn(false);
        
        String result = formatter.formatAsIndent(2);
        
        assertNotNull(result);
        assertTrue(result.contains("<html id=\"root\">"));
        assertTrue(result.contains("  <head id=\"head\">"));
        assertTrue(result.contains("    <title>"));
        assertTrue(result.contains("  <body id=\"body\">"));
    }
    
    @Test
    void testSpellCheckErrors() {
        when(mockEditor.isShowId()).thenReturn(true);
        when(mockSpellChecker.hasError(any(String.class))).thenReturn(true);
        
        String treeResult = formatter.formatAsTree();
        String indentResult = formatter.formatAsIndent(2);
        
        assertFalse(treeResult.contains("[X]"));
        assertFalse(indentResult.contains("[X]"));
    }
} 