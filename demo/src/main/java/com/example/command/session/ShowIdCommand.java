package com.example.command.session;

import com.example.command.Command;
import com.example.core.Editor;

public class ShowIdCommand implements Command {
    private final Editor editor;
    private final boolean show;
    private boolean previousState;

    public ShowIdCommand(Editor editor, boolean show){
        this.editor = editor;
        this.show = show;
    }

    @Override
    public void execute() {
        previousState = editor.isShowId();
        editor.setShowId(show);
    }

    @Override
    public void undo() {
        editor.setShowId(previousState);
    }

    @Override
    public boolean isReversible() {
        return true;
    }

}
