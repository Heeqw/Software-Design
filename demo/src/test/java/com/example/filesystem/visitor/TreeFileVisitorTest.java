package com.example.filesystem.visitor;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.core.SessionManager;
import com.example.filesystem.FileSystemNode;

class TreeFileVisitorTest {
    
    private TreeFileVisitor visitor;
    private SessionManager mockSessionManager;
    
    @BeforeEach
    void setUp() {
        mockSessionManager = mock(SessionManager.class);
        visitor = new TreeFileVisitor(mockSessionManager);
    }
    
    @Test
    void testVisitSingleFile() {
        FileSystemNode node = new FileSystemNode("test.txt", "/test.txt", false);
        visitor.visit(node);
        
        String expected = "└── test.txt\n";
        assertEquals(expected, visitor.getOutput());
    }
    
    @Test
    void testVisitModifiedFile() throws Exception {
        FileSystemNode node = new FileSystemNode("test.txt", "/test.txt", false);
        when(mockSessionManager.isFileModified("/test.txt")).thenReturn(true);
        
        visitor.visit(node);
        
        String expected = "└── test.txt*\n";
        assertEquals(expected, visitor.getOutput());
    }
    
    @Test
    void testVisitDirectory() {
        FileSystemNode child = new FileSystemNode("test.txt", "/dir/test.txt", false);
        FileSystemNode dir = new FileSystemNode("dir", "/dir", true);
        dir.setChildren(Collections.singletonList(child));
        
        visitor.visit(dir);
        
        String expected = 
            "└── dir\n" +
            "    └── test.txt\n";
        assertEquals(expected, visitor.getOutput());
    }
    
    @Test
    void testVisitComplexStructure() {
        // 创建复杂的目录结构
        FileSystemNode file1 = new FileSystemNode("file1.txt", "/dir1/file1.txt", false);
        FileSystemNode file2 = new FileSystemNode("file2.txt", "/dir1/dir2/file2.txt", false);
        FileSystemNode file3 = new FileSystemNode("file3.txt", "/dir1/dir2/file3.txt", false);
        
        FileSystemNode dir2 = new FileSystemNode("dir2", "/dir1/dir2", true);
        dir2.setChildren(Arrays.asList(file2, file3));
        
        FileSystemNode dir1 = new FileSystemNode("dir1", "/dir1", true);
        dir1.setChildren(Arrays.asList(file1, dir2));
        
        visitor.visit(dir1);
        
        String expected = 
            "└── dir1\n" +
            "    ├── file1.txt\n" +
            "    └── dir2\n" +
            "        ├── file2.txt\n" +
            "        └── file3.txt\n";
        assertEquals(expected, visitor.getOutput());
    }
} 