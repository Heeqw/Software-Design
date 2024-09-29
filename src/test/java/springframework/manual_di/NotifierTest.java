package springframework.manual_di;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import springframework.manual_di.core.Notifier;

public class NotifierTest {
    @Test
    public void emailer() {
        final StringBuffer content = new StringBuffer("");
        new Notifier(
                t -> true, // mock spell check
                t -> { // mock mail client
                    content.append(t);
                    return true;
                }).send(" text ");
        assertEquals("TEXT", content.toString());
    }
}
