package designpattern.command.livedemo.stringeditor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import designpattern.command.livedemo.command.AppendCommand;
import designpattern.command.livedemo.model.StringBuf;

public class AppendCommandTest {


    @Test
    public void testExecute() {
        StringBuf stringBuf = new StringBuf();
        AppendCommand appendCommand = new AppendCommand(stringBuf, "hello");
        appendCommand.execute();
        assertEquals("hello", stringBuf.getString());
        appendCommand.undo();
        assertEquals("", stringBuf.getString());
    }
}
