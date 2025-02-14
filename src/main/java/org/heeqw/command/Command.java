package org.heeqw.command;

public interface Command {
    void execute();
    CommandType getType();
}
