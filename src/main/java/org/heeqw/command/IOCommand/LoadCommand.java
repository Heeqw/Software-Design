package org.heeqw.command.IOCommand;

import org.heeqw.WorkspaceManager;

public class LoadCommand extends IOCommand {
    private final String filepath;
    
    public LoadCommand(String filepath) {
        this.filepath = filepath;
    }
    
    @Override
    public void execute() {
        WorkspaceManager.getInstance().createEditor(filepath);
    }
}




