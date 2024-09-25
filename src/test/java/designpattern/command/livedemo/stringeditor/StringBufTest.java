package designpattern.command.livedemo.stringeditor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import designpattern.command.livedemo.model.StringBuf;

public class StringBufTest {

    @Test
    public void testStringBufInsert_normal() {
        StringBuf stringBuf = new StringBuf("hello");
        stringBuf.insert(1, "~");
        assertEquals("h~ello", stringBuf.getString());
    }

    @Test
    public void testStringBufInsert_indexGreaterThanLength() {
        StringBuf stringBuf = new StringBuf("hello");
        stringBuf.insert(10, "~");
        assertEquals("hello~", stringBuf.getString());
    }

    @Test
    public void testStringBufInsert_indexLessThanZero() {
        StringBuf stringBuf = new StringBuf("hello");
        stringBuf.insert(-1, "~");
        assertEquals("hello", stringBuf.getString());
    }

}
