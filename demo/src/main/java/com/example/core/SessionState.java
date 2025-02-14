package com.example.core;

import java.util.List;
import java.util.Map;

public class SessionState {
    private List<String> openFiles;
    private String activeFile;
    private Map<String, Boolean> showIdSettings;

    public List<String> getOpenFiles() {
        return openFiles;
    }

    public String getActiveFile() {
        return activeFile;
    }

    public Map<String, Boolean> getShowIdSettings() {
        return showIdSettings;
    }

    public void setOpenFiles(List<String> openFiles) {
        this.openFiles = openFiles;
    }

    public void setActiveFile(String activeFile) {
        this.activeFile = activeFile;
    }

    public void setShowIdSettings(Map<String, Boolean> showIdSettings) {
        this.showIdSettings = showIdSettings;
    }
}
