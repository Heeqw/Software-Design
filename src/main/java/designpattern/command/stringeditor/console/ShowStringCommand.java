package designpattern.command.stringeditor.console;

import designpattern.command.stringeditor.command.Command;
import designpattern.command.stringeditor.model.StringBuf;

public class ShowStringCommand implements Command {

    @Override
    public void execute(StringBuf stringBuf) {
        System.out.println(stringBuf.getString());
    }

}
