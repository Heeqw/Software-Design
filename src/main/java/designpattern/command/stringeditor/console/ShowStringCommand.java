package designpattern.command.stringeditor.console;

import designpattern.command.stringeditor.command.Command;
import designpattern.command.stringeditor.model.StringBuf;

public class ShowStringCommand implements Command {

    private StringBuf stringBuf;

    public ShowStringCommand(StringBuf stringBuf) {
        this.stringBuf = stringBuf;
    }
    @Override
    public void execute() {
        System.out.println(stringBuf.getString());
    }

}
