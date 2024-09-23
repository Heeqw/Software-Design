package designpattern.command.stringeditor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import designpattern.command.stringeditor.command.AppendCommand;
import designpattern.command.stringeditor.command.Command;
import designpattern.command.stringeditor.command.DeleteCommand;
import designpattern.command.stringeditor.command.InsertCommand;
import designpattern.command.stringeditor.console.CommandParser;
import designpattern.command.stringeditor.console.InvalidCommandException;

public class CommandLineTest {

    @Test
    public void testCommandLine_Append_escaped() throws InvalidCommandException {

        Command c = CommandParser.parse("a '\\\\wor\\'ld\\\\'");
        assertEquals(new AppendCommand("\\wor\'ld\\"), c); // \wor'ld\
    }

    @Test
    public void testCommandLine_Insert() throws InvalidCommandException {

        Command c = CommandParser.parse("i 0 'world'");
        assertEquals(
                InsertCommand.create("world", 0), c);

    }

    @Test
    public void testCommandLine_Delete() throws InvalidCommandException {

        Command c = CommandParser.parse("d 5 6");

        assertEquals(
                new DeleteCommand(5, 11), c);
    }

    @Test
    public void testCommandLine_Error() throws InvalidCommandException {

        try {
            CommandParser.parse("x 5 6");
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command: x 5 6", e.getMessage());
        }
    }

}
