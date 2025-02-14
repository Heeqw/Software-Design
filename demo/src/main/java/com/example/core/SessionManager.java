package com.example.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.filesystem.FileSystemNode;
import com.example.filesystem.FileTreeBuilder;
import com.example.filesystem.HtmlFileNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SessionManager {
    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
    private static final String CONFIG_FILE = ".my-html";
    private static SessionManager instance;

    private final Map<String, Editor> openEditors;
    private Editor activeEditor;
    private final ObjectMapper objectMapper;

    private FileSystemNode fileTreeCache;
    private long lastTreeUpdateTime;
    private static final long CACHE_EXPIRE_TIME = 5000; // 5秒缓存过期

    private SessionManager() {
        this.openEditors = new HashMap<>();
        this.objectMapper = new ObjectMapper();
        this.lastTreeUpdateTime = 0;
        restoreSession();
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

        
    public Editor loadFile(String filePath) {
        // 统一转换为规范化的绝对路径
        Path normalizedPath = Paths.get(filePath).toAbsolutePath().normalize();
        String absolutePath = normalizedPath.toString();
        
        if (openEditors.containsKey(absolutePath)) {
            throw new IllegalStateException("File already opened: " + absolutePath);
        }
        
        // 检查是否是HTML文件
        if (!filePath.toLowerCase().endsWith(".html")) {
            throw new IllegalArgumentException("Not an HTML file: " + filePath);
        }

        FileTreeBuilder builder = new FileTreeBuilder(this);
        FileSystemNode node = builder.buildSingleFile(normalizedPath);
        
        if (!(node instanceof HtmlFileNode)) {
            throw new IllegalArgumentException("Failed to load as HTML file: " + filePath);
        }

        Editor editor = new Editor((HtmlFileNode) node);
        openEditors.put(absolutePath, editor);
        setActiveEditor(editor);
        invalidateFileTreeCache();
        saveSession();
        return editor;
    }
    public boolean closeEditor(Editor editor) {
        if (editor == null) {
            logger.warn("Attempted to close null editor");
            return false;
        }
        
        String absolutePath = normalizePath(editor.getFilePath());
        
        Editor removedEditor = openEditors.remove(absolutePath);
        if (removedEditor == null) {
            logger.warn("Editor was not in open editors list: {}", absolutePath);
            return false;
        }

        // 更新活动编辑器
        if (activeEditor == editor) {
            List<String> remainingFiles = new ArrayList<>(openEditors.keySet());
            if (!remainingFiles.isEmpty()) {
                activeEditor = openEditors.get(remainingFiles.get(0));
            } else {
                activeEditor = null;
            }
        }

        invalidateFileTreeCache();
        saveSession();
        
        logger.debug("Successfully closed editor. Open editors: {}", openEditors.keySet());
        return true;
    }

    public Editor getActiveEditor() {
        return activeEditor;
    }

    public void setActiveEditor(Editor editor) {
        if (!openEditors.containsValue(editor)) {
            throw new IllegalArgumentException("Editor is not managed by this session");
        }
        this.activeEditor = editor;
    }

    public List<Editor> getModifiedEditors() {
        return openEditors.values().stream()
                .filter(Editor::isModified)
                .collect(Collectors.toList());
    }

    public boolean isFileModified(String path) {
        try {
            // 将路径标准化为绝对路径
            String fullPath = findFullPath(path);
            Editor editor = openEditors.get(fullPath);
            return editor != null && editor.isModified();
        } catch (IllegalArgumentException e) {
            // 如果找不到对应的文件，返回false
            return false;
        }
    }

    public void saveSession() {
        SessionState state = new SessionState();
        state.setOpenFiles(new ArrayList<>(openEditors.keySet()));
        state.setActiveFile(activeEditor != null ? activeEditor.getFilePath() : null);
        state.setShowIdSettings(openEditors.entrySet().stream()
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    e -> e.getValue().isShowId()
                )));

        try {
            objectMapper.writeValue(new File(CONFIG_FILE), state);
        } catch (IOException e) {
            logger.error("Failed to save session state", e);
        }
    }

    private void restoreSession() {
        try {
            if (!Files.exists(Path.of(CONFIG_FILE))) {
                return;
            }

            SessionState state = objectMapper.readValue(new File(CONFIG_FILE), SessionState.class);
            
            // 恢复打开的文件
            for (String filePath : state.getOpenFiles()) {
                try {
                    Editor editor = loadFile(filePath);
                    editor.setShowId(state.getShowIdSettings().getOrDefault(filePath, true));
                } catch (Exception e) {
                    logger.warn("Failed to restore file: " + filePath, e);
                }
            }

            // 恢复活动编辑器
            if (state.getActiveFile() != null) {
                Editor editor = openEditors.get(state.getActiveFile());
                if (editor != null) {
                    setActiveEditor(editor);
                }
            }
        } catch (IOException e) {
            logger.error("Failed to restore session state", e);
        }
    }

    public List<String> getOpenFiles() {
        return new ArrayList<>(openEditors.keySet());
    }

    public boolean isFileOpen(String filepath) {
        return openEditors.containsKey(filepath);
    }

    public Editor getEditorByPath(String filepath) {
        // 尝试通过相对路径查找完整路径
        try {
            String fullPath = findFullPath(filepath);
            return openEditors.get(fullPath);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public FileSystemNode getFileTree() {
        long currentTime = System.currentTimeMillis();
        if (fileTreeCache == null || (currentTime - lastTreeUpdateTime) > CACHE_EXPIRE_TIME) {
            try {
                FileTreeBuilder builder = new FileTreeBuilder(this);
                fileTreeCache = builder.build();
                lastTreeUpdateTime = currentTime;
            } catch (Exception e) {
                logger.error("Failed to build file tree", e);
                return null;
            }
        }
        return fileTreeCache;
    }

    public void invalidateFileTreeCache() {
        fileTreeCache = null;
    }

    /**
     * 将给定的路径转换为规范化的绝对路径
     */
    private String normalizePath(String filePath) {
        return Paths.get(filePath).toAbsolutePath().normalize().toString();
    }
    
    /**
     * 根据部分路径查找完整的文件路径
     * 只支持绝对路径和相对路径
     */
    public String findFullPath(String partialPath) {
        // 如果是绝对路径，直接规范化
        if (Paths.get(partialPath).isAbsolute()) {
            return normalizePath(partialPath);
        }
        
        // 获取所有打开的文件
        List<String> openFiles = getOpenFiles();
        
        // 尝试将相对路径转换为绝对路径
        try {
            String normalizedPath = normalizePath(partialPath);
            if (openEditors.containsKey(normalizedPath)) {
                return normalizedPath;
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid path: " + partialPath);
        }
        
        throw new IllegalArgumentException("No matching file found for: " + partialPath);
    }
}
