package com.example.filesystem;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.jsoup.nodes.Document;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.example.exception.DocumentException;

class HtmlFileNodeTest {
    
    @TempDir
    Path tempDir;
    
    private HtmlFileNode htmlNode;
    private Path testFilePath;
    
    @BeforeEach
    void setUp() {
        testFilePath = tempDir.resolve("test.html");
        htmlNode = new HtmlFileNode("test.html", testFilePath.toString(), false);
    }
    
    @Test
    void testCreateNewFile() {
        Document doc = htmlNode.getHtmlDocument();
        
        assertNotNull(doc);
        assertEquals(1, doc.select("html").size());
        assertEquals(1, doc.select("head").size());
        assertEquals(1, doc.select("title").size());
        assertEquals(1, doc.select("body").size());
        
        // 验证基本ID是否已设置
        assertEquals("html", doc.selectFirst("html").id());
        assertEquals("head", doc.selectFirst("head").id());
        assertEquals("title", doc.selectFirst("title").id());
        assertEquals("body", doc.selectFirst("body").id());
    }
    
    @Test
    void testLoadExistingValidFile() throws IOException {
        // 创建一个有效的HTML文件
        String validHtml = "<html id='html'><head id='head'><title id='title'>Test</title></head><body id='body'>Content</body></html>";
        Files.writeString(testFilePath, validHtml, StandardCharsets.UTF_8);
        
        Document doc = htmlNode.getHtmlDocument();
        
        assertNotNull(doc);
        assertEquals("Test", doc.title());
        assertEquals("Content", doc.body().text());
    }
    
    @Test
    void testLoadInvalidFile() throws IOException {
        // 创建一个结构无效的HTML文件
        String invalidHtml = "<html><body>Missing head and title</body></html>";
        Files.writeString(testFilePath, invalidHtml, StandardCharsets.UTF_8);
        
        assertThrows(DocumentException.class, () -> {
            htmlNode.getHtmlDocument();
        });
    }
    
    @Test
    void testSaveDocument() throws IOException {
        Document doc = htmlNode.getHtmlDocument();
        doc.title("New Title");
        doc.body().text("New Content");
        
        htmlNode.saveDocument();
        
        // 验证文件是否被正确保存
        String savedContent = Files.readString(testFilePath, StandardCharsets.UTF_8);
        assertTrue(savedContent.contains("New Title"));
        assertTrue(savedContent.contains("New Content"));
    }
    
    @Test
    void testReloadDocument() throws IOException {
        // 首先获取文档并修改
        Document doc = htmlNode.getHtmlDocument();
        doc.title("Original Title");
        htmlNode.saveDocument();
        
        // 直接修改文件内容
        String newHtml = "<html id='html'><head id='head'><title id='title'>Updated Title</title></head><body id='body'>Updated</body></html>";
        Files.writeString(testFilePath, newHtml, StandardCharsets.UTF_8);
        
        // 重新加载文档
        htmlNode.reloadDocument();
        doc = htmlNode.getHtmlDocument();
        
        assertEquals("Updated Title", doc.title());
        assertEquals("Updated", doc.body().text());
    }
    

} 