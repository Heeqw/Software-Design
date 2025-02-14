package org.heeqw.command.EditCommand;

import org.heeqw.command.Command;
import org.heeqw.command.CommandType;
import org.heeqw.editor.DocumentState;
import org.heeqw.editor.HTMLEditor;


public abstract class EditCommand implements Command {
    protected DocumentState backup;
    protected final HTMLEditor editor;

    protected EditCommand(){
        this.editor = HTMLEditor.getInstance();
    }
    @Override
    public CommandType getType(){
        return CommandType.EDIT;
    }


}
