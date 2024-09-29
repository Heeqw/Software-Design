package springframework.container_di.mock;

import org.springframework.stereotype.Component;

import springframework.container_di.core.MailClient;

@Component
public class MailClientImpl implements MailClient {

    public MailClientImpl() {
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
