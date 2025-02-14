package com.example.filesystem.visitor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.core.SessionManager;
import com.example.filesystem.FileSystemNode;

public class TreeFileVisitor implements FileSystemVisitor {
    private static final Logger logger = LoggerFactory.getLogger(TreeFileVisitor.class);
    private final StringBuilder output = new StringBuilder();
    private final SessionManager sessionManager;
    private static final String VERTICAL = "│   ";
    private static final String CORNER = "└── ";
    private static final String TEE = "├── ";
    private static final String SPACE = "    ";

    public TreeFileVisitor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void visit(FileSystemNode node) {
        buildTreeString(node, "", true);
    }

    private void buildTreeString(FileSystemNode node, String prefix, boolean isLast) {
        output.append(prefix)
              .append(isLast ? CORNER : TEE)
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

        if (node.isDirectory() && node.getChildren() != null) {
            List<FileSystemNode> children = node.getChildren();
            for (int i = 0; i < children.size(); i++) {
                String newPrefix = prefix + (isLast ? SPACE : VERTICAL);
                buildTreeString(children.get(i), newPrefix, i == children.size() - 1);
            }
        }
    }

    @Override
    public void afterVisit(FileSystemNode node) {
        // 不需要特殊实现
    }

    @Override
    public String getOutput() {
        return output.toString();
    }
}
