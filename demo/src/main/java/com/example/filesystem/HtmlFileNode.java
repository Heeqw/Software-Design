package com.example.filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.exception.DocumentException;
import com.example.exception.FileSystemException;

public class HtmlFileNode extends FileSystemNode {
    private static final Logger logger = LoggerFactory.getLogger(HtmlFileNode.class);
    private Document htmlDocument;
    private boolean documentLoaded;

    public HtmlFileNode(String name, String relativePath, boolean isDirectory) {
        super(name, relativePath, isDirectory);
        this.documentLoaded = false;
    }

    public Document getHtmlDocument() {
        if (!documentLoaded) {
            loadDocument();
        }
        return htmlDocument;
    }

    private void loadDocument(){
        Path filePath = null;
        try {
            filePath = Paths.get(getPath()).toAbsolutePath().normalize();
            File file = filePath.toFile();
            
            if (!file.exists()) {
                htmlDocument = createEmptyDocument();
                saveDocument();
            } else {
                htmlDocument = Jsoup.parse(file, StandardCharsets.UTF_8.name());
                if (htmlDocument.html().trim().isEmpty()) {
                    htmlDocument = createEmptyDocument();
                }
                validateBasicStructure(htmlDocument);
                ensureBasicTagIds(htmlDocument);
            }
            documentLoaded = true;
        } catch (SecurityException e) {
            logger.error("Security error accessing file: " + getPath(), e);
            throw new FileSystemException(filePath, "Security error accessing file", e);
        } catch (IOException | IllegalArgumentException e) {
            logger.error("Failed to load HTML document: " + getPath(), e);
            htmlDocument = createEmptyDocument();
            documentLoaded = true;
        }
    }

    private Document createEmptyDocument(){
        Document doc = Jsoup.parse("<html><head><title></title></head><body></body></html>");
        ensureBasicTagIds(doc);
        return doc;
    }

    private void ensureBasicTagIds(Document doc) {
        Element html = doc.selectFirst("html");
        Element head = doc.head();
        Element title = doc.selectFirst("title");
        Element body = doc.body();
        
        if (html != null && !html.hasAttr("id")) {
            html.attr("id", "html");
        }
        if (head != null && !head.hasAttr("id")) {
            head.attr("id", "head");
        }
        if (title != null && !title.hasAttr("id")) {
            title.attr("id", "title");
        }
        if (body != null && !body.hasAttr("id")) {
            body.attr("id", "body");
        }
    }

    public void saveDocument() {
        if (!documentLoaded) {
            return;
        }
        try {
            File file = new File(getPath());
            File parentDir = file.getParentFile();
            if (parentDir != null) {
                parentDir.mkdirs();
            }

            // 设置输出格式
            Document.OutputSettings settings = htmlDocument.outputSettings()
                .prettyPrint(true)
                .indentAmount(2);
            
            // 获取格式化后的HTML
            String formattedHtml = htmlDocument
                .outputSettings(settings)
                .html();
            
            // 写入文件
            Files.writeString(file.toPath(), formattedHtml, StandardCharsets.UTF_8);
            setModified(false);
        } catch (IOException e) {
            logger.error("Failed to save HTML document: " + getPath(), e);
            throw new FileSystemException(
                Paths.get(getPath()), 
                "Failed to save HTML document", 
                e
            );
        }
    }

    public void reloadDocument() {
        documentLoaded = false;
        loadDocument();
    }

    private void validateBasicStructure(Document doc) {
        // 检查基本标签的数量
        if (doc.select("html").size() != 1) {
            throw new DocumentException("html", "Document must have exactly one <html> element");
        }
        if (doc.select("head").size() != 1) {
            throw new DocumentException("head", "Document must have exactly one <head> element");
        }
        if (doc.select("title").size() != 1) {
            throw new DocumentException("title", "Document must have exactly one <title> element");
        }
        if (doc.select("body").size() != 1) {
            throw new DocumentException("body", "Document must have exactly one <body> element");
        }

        // 检查结构
        Element html = doc.selectFirst("html");
        Element head = doc.selectFirst("head");
        Element body = doc.selectFirst("body");
        Element title = doc.selectFirst("title");

        if (!head.parent().equals(html) || !body.parent().equals(html)) {
            throw new DocumentException("html", "Invalid document structure: <head> and <body> must be direct children of <html>");
        }
        if (!title.parent().equals(head)) {
            throw new DocumentException("head", "Invalid document structure: <title> must be a child of <head>");
        }
    }
}
