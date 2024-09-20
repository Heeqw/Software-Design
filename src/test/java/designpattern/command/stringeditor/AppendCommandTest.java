package designpattern.command.stringeditor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import designpattern.command.stringeditor.command.AppendCommand;
import designpattern.command.stringeditor.model.StringBuf;

public class AppendCommandTest {

    @Test
    public void executeAndUndo() {
        StringBuf stringBuf = new StringBuf("Hello ");
        AppendCommand command = new AppendCommand(stringBuf, "World");
        command.execute();
        assertEquals("Hello World", stringBuf.getStr());

        command.undo();
        assertEquals("Hello ", stringBuf.getStr());
    }
}
