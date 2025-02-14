package com.example.command.display;

import com.example.command.Command;
import com.example.core.SessionManager;
import com.example.filesystem.FileSystemNode;
import com.example.filesystem.visitor.IndentFileVisitor;

public class DirIndentCommand implements Command {
    private final SessionManager sessionManager;
    private final int indentSize;

    public DirIndentCommand(SessionManager sessionManager, int indentSize){
        this.sessionManager = sessionManager;
        this.indentSize = indentSize;
    }

    @Override
    public void execute() {
        FileSystemNode root = sessionManager.getFileTree();
        IndentFileVisitor visitor = new IndentFileVisitor(sessionManager, indentSize);
        root.accept(visitor);
        System.out.println(visitor.getOutput());
    }

    @Override
    public void undo() {
        
    }

    @Override
    public boolean isReversible() {
        return false;
    }
}
