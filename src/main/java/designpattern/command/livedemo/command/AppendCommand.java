package designpattern.command.livedemo.command;

import designpattern.command.livedemo.model.StringBuf;

public class AppendCommand implements Command {


    private StringBuf stringBuf;

    private String str;

    public AppendCommand(StringBuf stringBuf, String str) {
        this.stringBuf = stringBuf;
        this.str = str;
    }

    @Override
    public void execute() {
        stringBuf.append(str);
    }

    @Override
    public void undo() {
        stringBuf.delete(stringBuf.getString().length() - str.length(), stringBuf.getString().length());
    }

}
