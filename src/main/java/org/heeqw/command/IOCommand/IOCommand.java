package org.heeqw.command.IOCommand;

import org.heeqw.command.Command;
import org.heeqw.command.CommandType;
import org.heeqw.editor.HTMLEditor;

public abstract class IOCommand implements Command {
    protected final HTMLEditor editor;

    protected IOCommand(){
        this.editor = HTMLEditor.getInstance();
    }
    @Override
    public CommandType getType(){
        return CommandType.IO;
    }


}
