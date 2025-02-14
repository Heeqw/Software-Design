package com.example.command.display;

import com.example.command.Command;
import com.example.core.SessionManager;
import com.example.filesystem.FileSystemNode;
import com.example.filesystem.visitor.TreeFileVisitor;

public class DirTreeCommand implements Command {
    private final SessionManager sessionManager;

    public DirTreeCommand(SessionManager sessionManager){
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute() {
        FileSystemNode root = sessionManager.getFileTree();
        TreeFileVisitor visitor = new TreeFileVisitor(sessionManager);

        root.accept(visitor);
        System.out.println(visitor.getOutput());
    }

    @Override
    public void undo() {

    }

    @Override
    public boolean isReversible(){
        return false;
    }
}
