package com.example.exception;

public class CommandException extends EditorException {
    private final String commandName;

    public CommandException(String commandName, String message) {
        super(String.format("Command '%s' failed: %s", commandName, message));
        this.commandName = commandName;
    }

    public CommandException(String commandName, String message, Throwable cause) {
        super(String.format("Command '%s' failed: %s", commandName, message), cause);
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
