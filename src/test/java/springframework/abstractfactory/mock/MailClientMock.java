package springframework.abstractfactory.mock;

import springframework.abstractfactory.core.MailClient;

public class MailClientMock implements MailClient {

    private static MailClient instance = new MailClientMock();

    public static MailClient getInstance() {
        return instance;
    }

    private String content;

    @Override
    public boolean sendMail(String content) {
        this.content = content;
        return true;
    }

    public String getContent() {
        return content;
    }

}
