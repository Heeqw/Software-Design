package springframework.abstractfactory.product;

import springframework.abstractfactory.core.MailClient;

public class MailClientImpl implements MailClient {
    private static MailClient instance = new MailClientImpl();

    public static MailClient getInstance() {
        return instance;
    }

    @Override
    public boolean sendMail(String upperCase) {
        // should use JavaMail API to send the message
        throw new UnsupportedOperationException("Unimplemented method 'sendMail'");
    }

}
