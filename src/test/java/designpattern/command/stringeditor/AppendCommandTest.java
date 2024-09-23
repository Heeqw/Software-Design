package designpattern.command.stringeditor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import designpattern.command.stringeditor.command.AppendCommand;
import designpattern.command.stringeditor.model.StringBuf;

public class AppendCommandTest {

    @Test
    public void executeAndUndo() {
        StringBuf stringBuf = new StringBuf("Hello ");
        AppendCommand command = new AppendCommand("World");
        command.execute(stringBuf);
        assertEquals("Hello World", stringBuf.getString());

        command.undo(stringBuf);
        assertEquals("Hello ", stringBuf.getString());
    }
}
