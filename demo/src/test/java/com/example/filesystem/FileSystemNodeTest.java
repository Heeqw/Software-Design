package com.example.filesystem;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;

import com.example.filesystem.visitor.FileSystemVisitor;

class FileSystemNodeTest {

    @Mock
    private FileSystemVisitor visitor;

    @InjectMocks
    private FileSystemNode fileSystemNode;

    private FileSystemNode childNode;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        childNode = new FileSystemNode("child", "/parent/child", false);
    }

    @Test
    void addChild_ChildIsAddedToParentChildrenList() {
        fileSystemNode.addChild(childNode);
        assertTrue(fileSystemNode.getChildren().contains(childNode), "Child should be added to parent's children list.");
    }

    @Test
    void addChild_ParentOfChildIsSetCorrectly() {
        fileSystemNode.addChild(childNode);
        assertEquals(fileSystemNode, childNode.getParent(), "Parent of the child should be set correctly.");
    }

    @Test
    void addChild_ChildIsNotAddedTwice() {
        fileSystemNode.addChild(childNode);
        fileSystemNode.addChild(childNode); // Adding child again
        List<FileSystemNode> children = fileSystemNode.getChildren();
        assertEquals(1, children.size(), "Child should not be added twice to the parent's children list.");
        assertTrue(children.contains(childNode), "The child should still be in the parent's children list.");
    }

    @Test
    void addChild_EmptyChildListWhenFirstAddingChild() {
        List<FileSystemNode> children = fileSystemNode.getChildren();
        assertEquals(0, children.size(), "Children list should be empty initially.");
        fileSystemNode.addChild(childNode);
        children = fileSystemNode.getChildren();
        assertEquals(1, children.size(), "Children list should contain one element after adding a child.");
    }

    @Test
    void setChildren_EmptyList_ClearsChildren() {
        fileSystemNode.setChildren(new ArrayList<>());
        assertTrue(fileSystemNode.getChildren().isEmpty(), "Children list should be empty after setting with an empty list.");
    }

    @Test
    void setChildren_NonEmptyList_SetsChildren() {
        List<FileSystemNode> newChildren = new ArrayList<>();
        newChildren.add(childNode);
        fileSystemNode.setChildren(newChildren);
        assertEquals(newChildren, fileSystemNode.getChildren(), "Children list should be set correctly.");
    }

    @Test
    void accept_VisitorCallsVisitAndAfterVisit() {
        fileSystemNode.accept(visitor);
        verify(visitor, times(1)).visit(fileSystemNode);
        verify(visitor, times(1)).afterVisit(fileSystemNode);
    }

    @Test
    void getName_ReturnsCorrectName() {
        assertEquals("parent", fileSystemNode.getName(), "The name should be 'parent'.");
    }

    @Test
    void getPath_ReturnsCorrectPath() {
        assertEquals("/parent", fileSystemNode.getPath(), "The path should be '/parent'.");
    }

    @Test
    void setModified_True_SetsModifiedFlag() {
        fileSystemNode.setModified(true);
        assertTrue(fileSystemNode.isModified(), "Modified flag should be true.");
    }

    @Test
    void setModified_False_SetsModifiedFlag() {
        fileSystemNode.setModified(false);
        assertFalse(fileSystemNode.isModified(), "Modified flag should be false.");
    }

    @Test
    void isDirectory_ReturnsCorrectValue() {
        assertTrue(fileSystemNode.isDirectory(), "The node should be a directory.");
    }

    @Test
    void isModified_ReturnsCorrectValue() {
        fileSystemNode.setModified(true);
        assertTrue(fileSystemNode.isModified(), "The node should be modified.");
    }

    @Test
    void getChildren_ReturnsChildrenList() {
        List<FileSystemNode> children = new ArrayList<>();
        children.add(childNode);
        fileSystemNode.setChildren(children);
        assertEquals(children, fileSystemNode.getChildren(), "The children list should be returned correctly.");
    }

    @Test
    void getParent_ReturnsParentNode() {
        fileSystemNode.addChild(childNode);
        assertEquals(fileSystemNode, childNode.getParent(), "The parent node should be returned correctly.");
    }
}