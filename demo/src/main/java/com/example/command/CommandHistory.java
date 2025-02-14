package com.example.command;
import java.util.Stack;
import com.example.exception.CommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandHistory {
    private static final Logger logger = LoggerFactory.getLogger(CommandHistory.class);
    private final Stack<Command> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();

    public void executeCommand(Command command) {
        try {
            command.execute();
            if (command.isReversible()) {
                undoStack.push(command);
                redoStack.clear();
            }
            logger.debug("Command executed: {}", command.getCommandName());
        } catch (Exception e) {
            logger.error("Failed to execute command: {}", command.getCommandName(), e);
            throw new CommandException(command.getCommandName(), "Execution failed", e);
        }
    }

    public void undo() {
        if (undoStack.isEmpty()) {
            throw new CommandException("Undo", "No command to undo");
        }

        Command command = undoStack.pop();
        try {
            command.undo();
            redoStack.push(command);
            logger.debug("Command undone: {}", command.getCommandName());
        } catch (Exception e) {
            logger.error("Failed to undo command: {}", command.getCommandName(), e);
            throw new CommandException(command.getCommandName(), "Undo failed", e);
        }
    }

    public void redo() {
        if (redoStack.isEmpty()) {
            throw new CommandException("Redo", "No command to redo");
        }

        Command command = redoStack.pop();
        try {
            command.execute();
            undoStack.push(command);
            logger.debug("Command redone: {}", command.getCommandName());
        } catch (Exception e) {
            logger.error("Failed to redo command: {}", command.getCommandName(), e);
            throw new CommandException(command.getCommandName(), "Redo failed", e);
        }
    }

    public boolean canUndo(){
        return !undoStack.isEmpty();
    }

    public boolean canRedo(){
        return !redoStack.isEmpty();
    }

    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }
}
