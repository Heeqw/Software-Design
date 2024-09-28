package designpattern.command.livedemo.stringeditor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import designpattern.command.livedemo.command.AppendCommand;
import designpattern.command.livedemo.model.StringBuf;

public class AppendCommandTest {


    @Test
    public void testAppendCommand() {
        StringBuf stringBuf = new StringBuf("hello");
        AppendCommand appendCommand = new AppendCommand(stringBuf, " world");
        appendCommand.execute();
        assertEquals("hello world", stringBuf.getString());
        appendCommand.undo();
        assertEquals("hello", stringBuf.getString());
    }

}
