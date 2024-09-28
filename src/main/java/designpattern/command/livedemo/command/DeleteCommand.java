package designpattern.command.livedemo.command;

import designpattern.command.livedemo.model.StringBuf;

public class DeleteCommand implements Command {
    private StringBuf stringBuf;
    private int start;
    private int end;

    private String deletedStr;

    public DeleteCommand(StringBuf stringBuf, int start, int end) {
        this.stringBuf = stringBuf;
        this.start = start;
        this.end = end;
    }

    public void execute() {
        deletedStr = stringBuf.getString().substring(start, end);
        stringBuf.delete(start, end);
    }
    public void undo() {
        stringBuf.insert(start, deletedStr);
    }

}
