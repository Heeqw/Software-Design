package designpattern.command.stringeditor;

import org.junit.Before;
import org.junit.Test;

import designpattern.command.stringeditor.model.StringBuf;

import static org.junit.Assert.assertEquals;

public class StringBufTest {

    private StringBuf stringBuf;

    @Before
    public void setUp() {
        stringBuf = new StringBuf("Hello World");
    }

    @Test
    public void delete_ValidRange_ShouldDeleteCharacters() {
        String deleted = stringBuf.delete(0, 5);
        assertEquals("Hello", deleted);
        assertEquals(" World", stringBuf.toString());
    }

    @Test
    public void delete_StartEqualsEnd_ShouldNotDeleteCharacters() {
        String deleted = stringBuf.delete(5, 5);
        assertEquals("", deleted);
        assertEquals("Hello World", stringBuf.toString());
    }

    @Test
    public void delete_EndOutOfBounds_ShouldDeleteToEndOfString() {
        String deleted = stringBuf.delete(0, 13);
        assertEquals("Hello World", deleted);
        assertEquals("", stringBuf.toString());
    }

    @Test
    public void delete_StartOutOfBounds_ShouldThrowIndexOutOfBoundsException() {
        try {
            stringBuf.delete(11, 10);
        } catch (IndexOutOfBoundsException e) {
            assertEquals(StringIndexOutOfBoundsException.class, e.getClass());
        }
    }

    @Test
    public void delete_EmptyString_ShouldReturnEmptyString() {
        StringBuf emptyStringBuf = new StringBuf("");
        String deleted = emptyStringBuf.delete(0, 0);
        assertEquals("", deleted);
        assertEquals("", emptyStringBuf.toString());
    }
}