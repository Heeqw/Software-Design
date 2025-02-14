package com.example.command.session;

import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import com.example.command.Command;
import com.example.core.Editor;
import com.example.exception.CommandException;

public class SaveCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(SaveCommand.class);

    private final Editor editor;
    private Path backupPath;

    public SaveCommand(Editor editor){
        this.editor = editor;
    }

    @Override
    public void execute() {
        try {
            if (editor.isModified()){
                createBackup();
            }
            editor.save();
            logger.info("Successfully saved file: {}", editor.getFilePath());
        } catch (Exception e) {
            logger.error("Failed to save file: {}", editor.getFilePath(), e);
            throw new CommandException(getCommandName(), "Failed to save file: " + e.getMessage());
        }
    }

    @Override
    public void undo() {
        if (backupPath != null && Files.exists(backupPath)){
            try {
                Path originalPath = Path.of(editor.getFilePath());
                Files.move(backupPath, originalPath, StandardCopyOption.REPLACE_EXISTING);
                editor.reloadDocument();
                backupPath = null;
            } catch (Exception e) {
                logger.error("Failed to revert to backup file: {}", editor.getFilePath(), e);
                throw new CommandException(getCommandName(),
                    "Failed to revert to backup file: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean isReversible() {
        return true;
    }

    private void createBackup(){
        try {
            Path originalPath = Path.of(editor.getFilePath());
            if (Files.exists(originalPath)){
                backupPath = Path.of(editor.getFilePath() + ".bak");
                Files.copy(originalPath, backupPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            logger.error("Failed to create backup file: {}", editor.getFilePath(), e);
            throw new CommandException(getCommandName(),
                "Failed to create backup file: " + e.getMessage());
        }
    }
}
