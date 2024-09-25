package designpattern.command.livedemo.command;

import designpattern.command.livedemo.model.StringBuf;

public class DeleteCommand implements Command {

    private StringBuf stringBuf;
    private int start;
    private int end;

    private String deletedString;

    public DeleteCommand(StringBuf stringBuf, int start, int end) {
        this.stringBuf = stringBuf;
        this.start = start;
        this.end = end;
    }

    @Override
    public void execute() {

        deletedString = stringBuf.substring(start, end);
        stringBuf.delete(start, end);
    }

    @Override
    public void undo() {
        stringBuf.insert(start, deletedString);
    }

}
