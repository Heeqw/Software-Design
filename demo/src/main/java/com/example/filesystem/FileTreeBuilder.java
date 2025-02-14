package com.example.filesystem;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.core.SessionManager;

public class FileTreeBuilder {
    private static final Logger logger = LoggerFactory.getLogger(FileTreeBuilder.class);
    private final SessionManager sessionManager;
    private final Set<Path> processedPaths;
    private final Path rootPath;
    private static final List<String> IGNORE_PATTERNS = Arrays.asList(
        "^\\..*",        // 隐藏文件
        "^node_modules$", // node模块
        "^target$",      // 编译输出
        "^bin$",         // 二进制文件
        "^build$"        // 构建输出
    );

    public FileTreeBuilder(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.processedPaths = new HashSet<>();
        this.rootPath = Paths.get(".").toAbsolutePath().normalize();
    }

    public FileSystemNode build() {
        processedPaths.clear();
        return buildNode(rootPath);
    }

    public FileSystemNode buildSingleFile(Path path) {
        return buildNode(path);
    }

    private FileSystemNode buildNode(Path path) {
        if (processedPaths.contains(path)) {
            return null;
        }
        processedPaths.add(path);

        String fileName = path.getFileName().toString();
        if (shouldIgnore(path)) {
            return null;
        }

        boolean isDirectory = Files.isDirectory(path);
        String relativePath = rootPath.relativize(path).toString();
        FileSystemNode node = createNode(path, relativePath.isEmpty() ? "." : fileName, isDirectory);

        if (isDirectory && !path.equals(rootPath.getParent())) {
            List<FileSystemNode> children = buildChildren(path);
            node.setChildren(children);
        }

        return node;
    }

    private List<FileSystemNode> buildChildren(Path directory) {
        List<FileSystemNode> children = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path child : stream) {
                FileSystemNode childNode = buildNode(child);
                if (childNode != null) {
                    children.add(childNode);
                }
            }
        } catch (IOException e) {
            logger.warn("Failed to process directory {}: {}", directory, e.getMessage());
        }
        
        children.sort(Comparator.<FileSystemNode>comparingInt(node -> node.isDirectory() ? 0 : 1)
                .thenComparing(FileSystemNode::getName));
        return children;
    }

    private FileSystemNode createNode(Path path, String fileName, boolean isDirectory) {
        try {
            if (!isDirectory && fileName.toLowerCase().endsWith(".html")) {
                return new HtmlFileNode(fileName, path.toString(), false);
            } else {
                return new FileSystemNode(fileName, path.toString(), isDirectory);
            }
        } catch (Exception e) {
            logger.warn("Failed to create node for {}: {}", path, e.getMessage());
            return new FileSystemNode(fileName, path.toString(), isDirectory);
        }
    }

    private boolean shouldIgnore(Path path) {
        String fileName = path.getFileName().toString();
        return IGNORE_PATTERNS.stream()
                .anyMatch(pattern -> fileName.matches(pattern));
    }
}
