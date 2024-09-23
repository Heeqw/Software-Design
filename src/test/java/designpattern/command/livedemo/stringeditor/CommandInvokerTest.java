package designpattern.command.livedemo.stringeditor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import designpattern.command.livedemo.command.AppendCommand;
import designpattern.command.livedemo.command.CommandInvoker;
import designpattern.command.livedemo.model.StringBuf;

public class CommandInvokerTest {

    @Test
    public void testUndoRedo() {
        StringBuf sb = new StringBuf("hello world");

        CommandInvoker invoker = new CommandInvoker();
        invoker.execute(new AppendCommand(sb, "!"));
        assertEquals("hello world!", sb.getStr());

        invoker.undo();
        assertEquals("hello world", sb.getStr());

        invoker.redo();
        assertEquals("hello world!", sb.getStr());

    }

}
