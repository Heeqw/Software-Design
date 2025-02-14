package com.example.filesystem.visitor;

import com.example.filesystem.FileSystemNode;

public interface FileSystemVisitor {
    void visit(FileSystemNode node);
    void afterVisit(FileSystemNode node);
    String getOutput();
}
