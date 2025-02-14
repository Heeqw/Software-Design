package com.example.display.visitor;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.spellcheck.SpellChecker;

class TreeDisplayVisitorTest {
    
    private TreeDisplayVisitor visitor;
    private SpellChecker mockSpellChecker;
    
    @BeforeEach
    void setUp() {
        mockSpellChecker = mock(SpellChecker.class);
        visitor = new TreeDisplayVisitor(true, mockSpellChecker);
    }
    
    @Test
    void testVisitElement() {
        Element element = new Element("div");
        element.attr("id", "test");
        element.text("Test content");
        
        when(mockSpellChecker.hasError(any(Element.class))).thenReturn(false);
        
        visitor.visit(element, 0);
        
        String result = visitor.getOutput();
        assertTrue(result.contains("<div id=\"test\">"));
        assertTrue(result.contains("Test content"));
    }
    
    @Test
    void testVisitElementWithSpellError() {
        Element element = new Element("div");
        element.text("Test content");
        
        when(mockSpellChecker.hasError(any(Element.class))).thenReturn(true);
        
        visitor.visit(element, 0);
        
        String result = visitor.getOutput();
        assertTrue(result.contains("[X]"));
    }
    
    @Test
    void testVisitTextNode() {
        TextNode textNode = new TextNode("Test text");
        when(mockSpellChecker.hasError(anyString())).thenReturn(false);
        
        visitor.visit(textNode, 1);
        
        String result = visitor.getOutput();
        assertTrue(result.contains("    └── \"Test text\""));
    }
    
    @Test
    void testVisitTextNodeWithSpellError() {
        TextNode textNode = new TextNode("Test text");
        when(mockSpellChecker.hasError(anyString())).thenReturn(true);
        
        visitor.visit(textNode, 1);
        
        String result = visitor.getOutput();
        assertTrue(result.contains("[X]"));
    }
    
    @Test
    void testNestedElements() {
        Element parent = new Element("div");
        Element child = new Element("span");
        parent.appendChild(child);
        
        when(mockSpellChecker.hasError(any(Element.class))).thenReturn(false);
        
        visitor.visit(parent, 0);
        
        String result = visitor.getOutput();
        assertTrue(result.contains("<div>"));
        assertTrue(result.contains("    └── <span>"));
    }
} 