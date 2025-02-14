package com.example.filesystem;

import java.util.ArrayList;
import java.util.List;

import com.example.filesystem.visitor.FileSystemVisitor;

public class FileSystemNode {
    private final String name;
    private final String path;
    private final boolean isDirectory;
    private final List<FileSystemNode> children;
    private boolean isModified;
    private FileSystemNode parent;

    public FileSystemNode(String name, String path, boolean isDirectory) {
        this.name = name;
        this.path = path;
        this.isDirectory = isDirectory;
        this.children = new ArrayList<>();
        this.isModified = false;
    }

    public void addChild(FileSystemNode child) {
        children.add(child);
        child.parent = this;
    }

    public void setChildren(List<FileSystemNode> children) {
        this.children.clear();
        this.children.addAll(children);
    }

    public void accept(FileSystemVisitor visitor) {
        visitor.visit(this);
        visitor.afterVisit(this);
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public void setModified(boolean modified) {
        this.isModified = modified;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public boolean isModified() {
        return isModified;
    }

    public List<FileSystemNode> getChildren() {
        return children;
    }

    public FileSystemNode getParent() {
        return parent;
    }
}
