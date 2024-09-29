package springframework.manual_di.product;

import springframework.manual_di.core.MailClient;

public class MailClientImpl implements MailClient {

    @Override
    public boolean sendMail(String upperCase) {
        // should use JavaMail API to send the message
        throw new UnsupportedOperationException("Unimplemented method 'sendMail'");
    }

}
