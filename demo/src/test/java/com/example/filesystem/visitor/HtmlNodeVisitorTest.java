package com.example.filesystem.visitor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.filesystem.FileSystemNode;
import com.example.filesystem.HtmlFileNode;

class HtmlNodeVisitorTest {
    
    private HtmlNodeVisitor visitorWithId;
    private HtmlNodeVisitor visitorWithoutId;
    
    @BeforeEach
    void setUp() {
        visitorWithId = new HtmlNodeVisitor(true);
        visitorWithoutId = new HtmlNodeVisitor(false);
    }
    
    @Test
    void testVisitHtmlFileWithId() {
        HtmlFileNode node = mock(HtmlFileNode.class);
        when(node.getName()).thenReturn("test.html");
        when(node.isDirectory()).thenReturn(false);
        
        Document doc = Jsoup.parse(
            "<html id='root'><head id='head'><title id='title'>Test</title></head>" +
            "<body id='body'>Content</body></html>"
        );
        when(node.getHtmlDocument()).thenReturn(doc);
        
        visitorWithId.visit(node);
        
        String expected = 
            "File: test.html\n" +
            "<#root>\n" +
            "  <html id=\"root\">\n" +
            "    <head id=\"head\">\n" +
            "      <title id=\"title\">\n" +
            "        Test\n" +
            
            "    <body id=\"body\">\n" +
            "      Content\n" ;
        assertEquals(expected, visitorWithId.getOutput());
    }
    
    @Test
    void testVisitHtmlFileWithoutId() {
        HtmlFileNode node = mock(HtmlFileNode.class);
        when(node.getName()).thenReturn("test.html");
        when(node.isDirectory()).thenReturn(false);
        
        Document doc = Jsoup.parse(
            "<html id='root'><head id='head'><title id='title'>Test</title></head>" +
            "<body id='body'>Content</body></html>"
        );
        when(node.getHtmlDocument()).thenReturn(doc);
        
        visitorWithoutId.visit(node);
        
        String expected = 
            "File: test.html\n" +
            "<#root>\n" +
            "  <html>\n" +
            "    <head>\n" +
            "      <title>\n" +
            "        Test\n" +
            
            "    <body>\n" +
            "      Content\n";
        assertEquals(expected, visitorWithoutId.getOutput());
    }
    
    @Test
    void testVisitDirectory() {
        FileSystemNode dir = new FileSystemNode("testDir", "/testDir", true);
        HtmlFileNode htmlFile = mock(HtmlFileNode.class);
        when(htmlFile.getName()).thenReturn("test.html");
        when(htmlFile.isDirectory()).thenReturn(false);
        
        Document doc = Jsoup.parse("<html><head><title>Test</title></head><body>Content</body></html>");
        when(htmlFile.getHtmlDocument()).thenReturn(doc);
        
        dir.setChildren(java.util.Collections.singletonList(htmlFile));
        
        visitorWithoutId.visit(dir);
        
        String expected = 
            "Directory: testDir\n" +
            "File: test.html\n" +
            "<#root>\n" +
            "  <html>\n" +
            "    <head>\n" +
            "      <title>\n" +
            "        Test\n" +
            
            "    <body>\n" +
            "      Content\n";
        assertEquals(expected, visitorWithoutId.getOutput());
    }
    
    @Test
    void testVisitEmptyDirectory() {
        FileSystemNode dir = new FileSystemNode("emptyDir", "/emptyDir", true);
        dir.setChildren(java.util.Collections.emptyList());
        
        visitorWithId.visit(dir);
        
        String expected = "Directory: emptyDir\n";
        assertEquals(expected, visitorWithId.getOutput());
    }
    
    @Test
    void testVisitComplexHtmlStructure() {
        HtmlFileNode node = mock(HtmlFileNode.class);
        when(node.getName()).thenReturn("complex.html");
        when(node.isDirectory()).thenReturn(false);
        
        Document doc = Jsoup.parse(
            "<html><head><title>Complex</title></head>" +
            "<body><div id='main'><p>Para 1</p><p>Para 2</p></div></body></html>"
        );
        when(node.getHtmlDocument()).thenReturn(doc);
        
        visitorWithId.visit(node);
        
        String expected = 
            "File: complex.html\n" +
            "<#root>\n" +
            "  <html>\n" +
            "    <head>\n" +
            "      <title>\n" +
            "        Complex\n" +
            
            "    <body>\n" +
            "      <div id=\"main\">\n" +
            "        <p>\n" +
            "          Para 1\n" +

            "        <p>\n" +
            "          Para 2\n" ;
        assertEquals(expected, visitorWithId.getOutput());
    }
} 