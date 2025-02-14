package com.example.command.session;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.command.Command;
import com.example.core.Editor;
import com.example.core.SessionManager;
import com.example.exception.CommandException;

public class LoadCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(LoadCommand.class);

    private final SessionManager sessionManager;
    private final String filePath;
    private Editor loadedEditor;

    public LoadCommand(SessionManager sessionManager, String filePath){
        this.sessionManager = sessionManager;
        this.filePath = filePath;
    }

    @Override
    public void execute() {
        try {
            Path normalizedPath = Paths.get(filePath).toAbsolutePath().normalize();

            if (sessionManager.isFileOpen(normalizedPath.toString())){
                throw new CommandException("LoadCommand", "File is already open: " + filePath);
            }

            loadedEditor = sessionManager.loadFile(normalizedPath.toString());
            logger.info("Successfully loaded file: {}", filePath);

        } catch (Exception e) {
            logger.error("Failed to load file: {}", filePath, e);
            throw new CommandException(getCommandName(),
                "Failed to load file: " + e.getMessage());
        }
    }

    @Override
    public void undo() {
        if (loadedEditor != null) {
            try {
                sessionManager.closeEditor(loadedEditor);
                loadedEditor = null;
            } catch (Exception e) {
                logger.error("Failed to undo load command for file: {}", filePath, e);
                throw new CommandException(getCommandName(),
                    "Failed to undo load command: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean isReversible(){
        return true;
    }

}
