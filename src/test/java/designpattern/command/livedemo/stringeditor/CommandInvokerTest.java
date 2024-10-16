package designpattern.command.livedemo.stringeditor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import designpattern.command.livedemo.command.AppendCommand;
import designpattern.command.livedemo.command.CommandInvoker;
import designpattern.command.livedemo.model.StringBuf;

public class CommandInvokerTest {

    @Test
    public void testInvoker() {
        StringBuf stringBuf = new StringBuf("hello");
        CommandInvoker invoker = new CommandInvoker();
        AppendCommand appendCommand = new AppendCommand(stringBuf, " world");
        invoker.execute(appendCommand);
        assertEquals("hello world", stringBuf.getString());
        invoker.undo();
        assertEquals("hello", stringBuf.getString());
        invoker.redo();
        assertEquals("hello world", stringBuf.getString());
    }
    
}
