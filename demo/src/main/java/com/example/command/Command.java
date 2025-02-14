package com.example.command;

public interface Command {
    void execute();
    void undo();
    boolean isReversible();
    default String getCommandName() {
        return getClass().getSimpleName();
    }
}
