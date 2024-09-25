package designpattern.command.livedemo.stringeditor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import designpattern.command.livedemo.command.DeleteCommand;
import designpattern.command.livedemo.model.StringBuf;

public class DeleteCommandTest {

    @Test
    public void testExecute() {
        StringBuf sb = new StringBuf("hello world");
        DeleteCommand cmd = new DeleteCommand(sb, 6, 11);
        cmd.execute();
        assertEquals("hello ", sb.getString());
        cmd.undo();
        assertEquals("hello world", sb.getString());
    }

}
