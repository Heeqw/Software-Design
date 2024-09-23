package designpattern.command.livedemo.stringeditor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import designpattern.command.livedemo.model.StringBuf;

public class StringBufTest {

    @Test
    public void stringBuffer_delete() {

        StringBuf sb = new StringBuf("hello world");
        sb.delete(0, 5);
        assertEquals(" world", sb.getStr());

    }
}
