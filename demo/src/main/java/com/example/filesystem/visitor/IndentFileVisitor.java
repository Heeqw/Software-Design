package com.example.filesystem.visitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.core.SessionManager;
import com.example.filesystem.FileSystemNode;

public class IndentFileVisitor implements FileSystemVisitor {
    private static final Logger logger = LoggerFactory.getLogger(IndentFileVisitor.class);
    private final StringBuilder output = new StringBuilder();
    private final SessionManager sessionManager;
    private final int indentSize;

    public IndentFileVisitor(SessionManager sessionManager, int indentSize) {
        this.sessionManager = sessionManager;
        this.indentSize = indentSize;
    }

    @Override
    public void visit(FileSystemNode node) {
        buildIndentString(node, 0);
    }

    private void buildIndentString(FileSystemNode node, int depth) {
        String indent = " ".repeat(depth * indentSize);
        output.append(indent)
              .append(node.getName());

        if (!node.isDirectory()) {
            try {
                if (sessionManager.isFileModified(node.getPath())) {
                    output.append("*");
                }
            } catch (Exception e) {
                logger.debug("Failed to check modification status for: {}", node.getPath());
            }
        }
        output.append("\n");

        if (node.isDirectory()) {
            node.getChildren().forEach(child -> 
                buildIndentString(child, depth + 1)
            );
        }
    }

    @Override
    public void afterVisit(FileSystemNode node) {
        // 由于我们在visit方法中已经处理了所有的递归逻辑
        // 这里不需要特别的实现
    }

    @Override
    public String getOutput() {
        return output.toString();
    }
}
