package designpattern.command.livedemo.stringeditor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import designpattern.command.livedemo.command.DeleteCommand;
import designpattern.command.livedemo.model.StringBuf;

public class DeleteCommandTest {
    @Test
    public void testDelete() {
        StringBuf stringBuf = new StringBuf("hello world");
        DeleteCommand cmd = new DeleteCommand(stringBuf, 6, 11);
        cmd.execute();
        System.out.println(stringBuf.getString()); // "hello"
        assertEquals("hello ", stringBuf.getString());
    }
}
