package designpattern.command.livedemo.stringeditor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import designpattern.command.livedemo.command.AppendCommand;
import designpattern.command.livedemo.command.CommandInvoker;
import designpattern.command.livedemo.model.StringBuf;

public class CommandInvoderTest {

    @Test
    public void testInvoker() {
        StringBuf buf = new StringBuf("hello world");
        CommandInvoker invoker = new CommandInvoker();
        invoker.execute(new AppendCommand(buf, " java"));
        invoker.execute(new AppendCommand(buf, "!"));
        System.out.println(buf.getString()); // hello worldjava
        assertEquals("hello world java!", buf.getString());
        invoker.undo();
        assertEquals("hello world java", buf.getString());
        invoker.undo();
        assertEquals("hello world", buf.getString());
        invoker.redo();
        assertEquals("hello world java", buf.getString());
    }
}
