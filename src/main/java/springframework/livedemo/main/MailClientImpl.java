package springframework.livedemo.main;

import org.springframework.stereotype.Service;

import springframework.livedemo.core.MailClient;

@Service
public class MailClientImpl implements MailClient {

    @Override
    public boolean sendMail(String message) {
        // should use JavaMail API to send the message
        System.out.println("Sending email with message: " + message.toUpperCase());
        return true;
    }

}
