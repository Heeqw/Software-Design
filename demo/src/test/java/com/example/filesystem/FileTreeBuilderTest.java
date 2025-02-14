package com.example.filesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.core.SessionManager;

class FileTreeBuilderTest {
    
    @TempDir
    Path tempDir;
    
    private FileTreeBuilder fileTreeBuilder;
    private SessionManager mockSessionManager;
    
    @BeforeEach
    void setUp() {
        mockSessionManager = mock(SessionManager.class);
        fileTreeBuilder = new FileTreeBuilder(mockSessionManager);
    }
    
    @Test
    void testBuildSingleFile() throws IOException {
        // 创建测试文件
        Path htmlFile = tempDir.resolve("test.html");
        Files.writeString(htmlFile, "<html><head><title>Test</title></head><body></body></html>");
        
        FileSystemNode node = fileTreeBuilder.buildSingleFile(htmlFile);
        
        assertNotNull(node);
        assertTrue(node instanceof HtmlFileNode);
        assertEquals("test.html", node.getName());
        assertFalse(node.isDirectory());
    }
    
    @Test
    void testBuildDirectoryStructure() throws IOException {
        // 创建测试目录结构
        Path subDir = Files.createDirectory(tempDir.resolve("subdir"));
        Path htmlFile = Files.createFile(subDir.resolve("test.html"));
        Path txtFile = Files.createFile(subDir.resolve("test.txt"));
        
        FileSystemNode root = fileTreeBuilder.buildSingleFile(tempDir);
        
        assertNotNull(root);
        assertTrue(root.isDirectory());
        
        List<FileSystemNode> children = root.getChildren();
        assertEquals(1, children.size()); // 只有一个子目录
        
        FileSystemNode subDirNode = children.get(0);
        assertEquals("subdir", subDirNode.getName());
        assertTrue(subDirNode.isDirectory());
        
        List<FileSystemNode> subDirChildren = subDirNode.getChildren();
        assertEquals(2, subDirChildren.size());
        
        // 验证文件排序（目录在前，文件按名称排序）
        assertTrue(subDirChildren.get(0) instanceof HtmlFileNode);
        assertEquals("test.html", subDirChildren.get(0).getName());
        assertEquals("test.txt", subDirChildren.get(1).getName());
    }
    
    @Test
    void testIgnorePatterns() throws IOException {
        // 创建应该被忽略的文件和目录
        Files.createDirectory(tempDir.resolve("node_modules"));
        Files.createDirectory(tempDir.resolve("target"));
        Files.createFile(tempDir.resolve(".gitignore"));
        
        // 创建不应该被忽略的文件
        Files.createFile(tempDir.resolve("normal.txt"));
        
        FileSystemNode root = fileTreeBuilder.buildSingleFile(tempDir);
        List<FileSystemNode> children = root.getChildren();
        
        // 只应该有一个普通文件
        assertEquals(1, children.size());
        assertEquals("normal.txt", children.get(0).getName());
    }
    
    @Test
    void testBuildWithEmptyDirectory() throws IOException {
        Path emptyDir = Files.createDirectory(tempDir.resolve("empty"));
        
        FileSystemNode root = fileTreeBuilder.buildSingleFile(tempDir);
        List<FileSystemNode> children = root.getChildren();
        
        assertEquals(1, children.size());
        FileSystemNode emptyDirNode = children.get(0);
        assertTrue(emptyDirNode.isDirectory());
        assertTrue(emptyDirNode.getChildren().isEmpty());
    }
    
    @Test
    void testBuildWithInaccessibleDirectory() throws IOException {
        // 模拟一个无法访问的目录
        Path mockPath = mock(Path.class);
        when(mockPath.getFileName()).thenReturn(mockPath);
        when(mockPath.toString()).thenReturn("inaccessible");
        when(Files.isDirectory(mockPath)).thenReturn(true);
        when(Files.newDirectoryStream(mockPath)).thenThrow(new IOException("Access denied"));
        
        FileSystemNode node = fileTreeBuilder.buildSingleFile(mockPath);
        
        assertNotNull(node);
        assertTrue(node.isDirectory());
        assertTrue(node.getChildren().isEmpty());
    }
    
    @Test
    void testHtmlFileCreation() throws IOException {
        // 创建HTML文件和普通文件
        Files.createFile(tempDir.resolve("test.html"));
        Files.createFile(tempDir.resolve("test.txt"));
        
        FileSystemNode root = fileTreeBuilder.buildSingleFile(tempDir);
        List<FileSystemNode> children = root.getChildren();
        
        assertEquals(2, children.size());
        assertTrue(children.get(0) instanceof HtmlFileNode);
        assertFalse(children.get(1) instanceof HtmlFileNode);
    }
    
    @Test
    void testRebuild() throws IOException {
        // 首次构建
        Files.createFile(tempDir.resolve("initial.txt"));
        FileSystemNode firstBuild = fileTreeBuilder.buildSingleFile(tempDir);
        assertEquals(1, firstBuild.getChildren().size());
        
        // 添加新文件后重新构建
        Files.createFile(tempDir.resolve("new.txt"));
        FileSystemNode secondBuild = fileTreeBuilder.buildSingleFile(tempDir);
        assertEquals(2, secondBuild.getChildren().size());
    }
    
    @Test
    void testBuildWithSymbolicLink() throws IOException {
        // 创建目标文件和符号链接
        Path targetFile = Files.createFile(tempDir.resolve("target.txt"));
        Path link = tempDir.resolve("link.txt");
        try {
            Files.createSymbolicLink(link, targetFile);
            
            FileSystemNode root = fileTreeBuilder.buildSingleFile(tempDir);
            List<FileSystemNode> children = root.getChildren();
            
            assertEquals(2, children.size());
        } catch (UnsupportedOperationException e) {
            // 如果系统不支持符号链接，则跳过测试
            System.out.println("Symbolic links not supported on this system");
        }
    }
} 