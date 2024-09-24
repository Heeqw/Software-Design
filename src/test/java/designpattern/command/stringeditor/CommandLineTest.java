package designpattern.command.stringeditor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import designpattern.command.stringeditor.command.CommandInvoker;
import designpattern.command.stringeditor.command.AppendCommand;
import designpattern.command.stringeditor.command.Command;
import designpattern.command.stringeditor.command.DeleteCommand;
import designpattern.command.stringeditor.command.InsertCommand;
import designpattern.command.stringeditor.console.CommandParser;
import designpattern.command.stringeditor.console.InvalidCommandException;
import designpattern.command.stringeditor.model.StringBuf;

public class CommandLineTest {

    @Test
    public void testCommandLine_Append_escaped() throws InvalidCommandException {
        StringBuf stringBuf = new StringBuf("Hello");
        CommandInvoker invoker = new CommandInvoker();

        Command c = CommandParser.parse(stringBuf, invoker, "a '\\\\wor\\'ld\\\\'");
        assertEquals(new AppendCommand(stringBuf, "\\wor\'ld\\"), c); // \wor'ld\
    }

    @Test
    public void testCommandLine_Insert() throws InvalidCommandException {
        StringBuf stringBuf = new StringBuf("Hello");
        CommandInvoker invoker = new CommandInvoker();

        Command c = CommandParser.parse(stringBuf, invoker, "i 0 'world'");
        assertEquals(
                InsertCommand.create(stringBuf, "world", 0), c);

    }

    @Test
    public void testCommandLine_Delete() throws InvalidCommandException {
        StringBuf stringBuf = new StringBuf("Hello");
        CommandInvoker invoker = new CommandInvoker();

        Command c = CommandParser.parse(stringBuf, invoker, "d 5 6");

        assertEquals(
                new DeleteCommand(stringBuf, 5, 11), c);
    }

    @Test
    public void testCommandLine_Error() throws InvalidCommandException {

        StringBuf stringBuf = new StringBuf("Hello");
        CommandInvoker invoker = new CommandInvoker();

        try {
            CommandParser.parse(stringBuf, invoker, "x 5 6");
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command: x 5 6", e.getMessage());
        }
    }

}
