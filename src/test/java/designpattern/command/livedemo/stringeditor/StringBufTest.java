package designpattern.command.livedemo.stringeditor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import designpattern.command.livedemo.model.StringBuf;

public class StringBufTest {

    @Test
    public void testStringBuf_delete_normal() {
        StringBuf stringBuf = new StringBuf("hello world");
        stringBuf.delete(3, 7);
        assertEquals("helorld", stringBuf.getString());
    }

}
