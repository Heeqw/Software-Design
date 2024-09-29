package springframework.container_di.product;

import org.springframework.stereotype.Component;

import springframework.container_di.core.MailClient;

@Component
public class MailClientImpl implements MailClient {

    @Override
    public boolean sendMail(String text) {
        // should use JavaMail API to send the message
        System.out.println("send mail : " + text);
        return true;
    }

}
