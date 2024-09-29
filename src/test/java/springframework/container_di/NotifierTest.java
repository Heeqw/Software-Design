package springframework.container_di;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import springframework.container_di.core.Notifier;
import springframework.container_di.mock.MailClientImpl;
import springframework.container_di.mock.MockConfig;

@SpringBootTest
@ContextConfiguration(classes = MockConfig.class)
public class NotifierTest {
    @Autowired
    MailClientImpl mailClient;

    @Autowired
    Notifier notifier;

    @Test
    public void emailer() {
        notifier.send(" text ");
        assertEquals("TEXT", mailClient.getContent());
    }
}
