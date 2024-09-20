package designpattern.command.stringeditor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import designpattern.command.stringeditor.command.DeleteCommand;
import designpattern.command.stringeditor.model.StringBuf;

public class DeleteCommandTest {

    @Test
    public void deleteCommandTest() {

        StringBuf stringBuf = new StringBuf("hello world");
        DeleteCommand deleteCommand = new DeleteCommand(stringBuf, 0, 5);
        deleteCommand.execute();
        assertEquals(" world", stringBuf.getStr());
        deleteCommand.undo();
        assertEquals("hello world", stringBuf.getStr());
    }

}
