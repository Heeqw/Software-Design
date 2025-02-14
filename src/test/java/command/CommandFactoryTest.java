package command;

import org.heeqw.command.Command;
import org.heeqw.command.CommandFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandFactoryTest {
    @Test
    void testCreateAppendCommand() {
        Command cmd = CommandFactory.createCommand("append", "div", "test", "body", "content");
        assertNotNull(cmd);
        assertEquals("AppendCommand", cmd.getClass().getSimpleName());
    }

    @Test
    void testCreateInvalidCommand() {
        assertThrows(IllegalArgumentException.class, () ->
                CommandFactory.createCommand("invalid", "arg1", "arg2")
        );
    }
}
