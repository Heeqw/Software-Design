package designpattern.command.stringeditor.command;

import designpattern.command.stringeditor.model.StringBuf;

public class DeleteCommand implements Command {

    private StringBuf stringBuf;
    private int start;
    private int end;
    private String deletedText;

    public DeleteCommand(StringBuf stringBuf2, int start, int end) {
        this.stringBuf = stringBuf2;
        this.start = start;
        this.end = end;
    }

    public void execute() {
        deletedText = stringBuf.delete(start, end);
    }

    public void undo() {
        stringBuf.insert(deletedText, start);
    }

}
