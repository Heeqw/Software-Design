package com.example.core;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.command.Command;
import com.example.command.CommandHistory;
import com.example.filesystem.HtmlFileNode;

public class Editor {
    private static final Logger logger = LoggerFactory.getLogger(Editor.class);

    private final HtmlFileNode fileNode;
    private final CommandHistory history;
    private boolean showId;
    private boolean modified = false;
    private String savedContent;

    public Editor(HtmlFileNode fileNode) {
        this.fileNode = fileNode;
        this.history = new CommandHistory();
        this.showId = true;
        this.savedContent = fileNode.getHtmlDocument().html();
    }

    public void executeCommand(Command command) {
        try {
            history.executeCommand(command);
            checkModifiedState();
        } catch (Exception e) {
            throw e;
        }
    }

    public void undo() {
        try {
            history.undo();
            checkModifiedState();
        } catch (Exception e) {
            throw e;
        }
    }

    public void redo() {
        try {
            history.redo();
            checkModifiedState();
        } catch (Exception e) {
            throw e;
        }
    }

    private void checkModifiedState() {
        String currentContent = getDocument().html();
        this.modified = !currentContent.equals(savedContent);
    }

    public void save() {
        fileNode.saveDocument();
        savedContent = getDocument().html();
        this.modified = false;
    }

    public boolean isModified() {
        checkModifiedState();
        return modified;
    }

    public Document getDocument() {
        return fileNode.getHtmlDocument();
    }

    public String getFilePath() {
        return fileNode.getPath();
    }

    public boolean isShowId() {
        return showId;
    }

    public void setShowId(boolean showId) {
        this.showId = showId;
    }

    public void markModified() {
        this.modified = true;
    }

    public String getContent() {
        return getDocument().html();
    }

    public void reloadDocument() {
        if (fileNode != null) {
            fileNode.reloadDocument();
            resetModifiedState();
        }
    }

    public void resetModifiedState() {
        if (fileNode != null) {
            fileNode.setModified(false);
        }
        this.modified = false;
    }
    
    
    
}


