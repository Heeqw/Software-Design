package springframework.abstractfactory;

import static org.junit.Assert.assertEquals;
import springframework.abstractfactory.core.Notifier;
import springframework.abstractfactory.mock.MailClientMock;
import springframework.abstractfactory.mock.MockNotifierServiceFactory;

import org.junit.Test;

public class NotifierTest {
    @Test
    public void emailer() {
        new Notifier(
                MockNotifierServiceFactory.getInstance()).send(" text ");
        String content = ((MailClientMock) MockNotifierServiceFactory.getInstance().getMailClient()).getContent();
        assertEquals("TEXT", content.toString());
    }

}
