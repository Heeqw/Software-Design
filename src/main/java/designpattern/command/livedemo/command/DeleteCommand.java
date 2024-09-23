package designpattern.command.livedemo.command;

import designpattern.command.livedemo.model.StringBuf;

public class DeleteCommand implements Command {

    private StringBuf stringBuf;
    private int start;
    private int end;
    private String deletedStr;

    public DeleteCommand(StringBuf stringBuf2, int start, int end) {
        this.stringBuf = stringBuf2;
        this.start = start;
        this.end = end;
    }

    @Override
    public void execute() {
        deletedStr = stringBuf.delete(start, end);
    }

    @Override
    public void undo() {
        stringBuf.insert(start, deletedStr);
    }

}
