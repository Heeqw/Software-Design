package designpattern.command.stringeditor.command;

import designpattern.command.stringeditor.model.StringBuf;

public class AppendCommand implements Command {
    private final StringBuf stringBuf;
    private final String text;
    private int previousLength;

    public AppendCommand(StringBuf stringBuf, String text) {
        this.stringBuf = stringBuf;
        this.text = text;
    }

    @Override
    public void execute() {
        previousLength = stringBuf.length();
        stringBuf.append(text);
    }

    @Override
    public void undo() {
        stringBuf.delete(previousLength, stringBuf.length());
    }
}