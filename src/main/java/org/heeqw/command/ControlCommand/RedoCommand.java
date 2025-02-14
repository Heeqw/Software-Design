package org.heeqw.command.ControlCommand;

import org.heeqw.command.Command;
import org.heeqw.command.CommandType;

public class RedoCommand implements Command {
    @Override
    public void execute(){

    }

    @Override
    public CommandType getType(){
        return CommandType.CONTROL;
    }
}
