package designpattern.command.stringeditor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import designpattern.command.stringeditor.command.AppendCommand;
import designpattern.command.stringeditor.command.CommandInvoker;
import designpattern.command.stringeditor.model.StringBuf;

public class CommandInvokerTest {

    @Test
    public void testCommandInvoker_append() {
        StringBuf stringBuf = new StringBuf("Hello");
        CommandInvoker commandInvoker = new CommandInvoker();

        commandInvoker.storeAndExecute(new AppendCommand(stringBuf, " World"));
        commandInvoker.storeAndExecute(new AppendCommand(stringBuf, " Again"));
        commandInvoker.undoLastCommand();
        assertEquals("Hello World", stringBuf.getString());
        commandInvoker.undoLastCommand();
        assertEquals("Hello", stringBuf.getString());
        commandInvoker.redoLastCommand();
        assertEquals("Hello World", stringBuf.getString());

    }
}
