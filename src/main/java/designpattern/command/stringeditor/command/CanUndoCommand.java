package designpattern.command.stringeditor.command;

import designpattern.command.stringeditor.model.StringBuf;

public interface CanUndoCommand extends Command {
    void undo(StringBuf stringBuf);

}
