package org.heeqw.command.DisplayCommand;


import org.heeqw.command.Command;
import org.heeqw.command.CommandType;
import org.heeqw.editor.HTMLEditor;

public abstract class DisplayCommand implements Command {
    protected final HTMLEditor editor;

    protected DisplayCommand(){
        this.editor = HTMLEditor.getInstance();
    }
    @Override
    public CommandType getType(){
        return CommandType.DISPLAY;
    }


}
