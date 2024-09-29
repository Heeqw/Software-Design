package springframework.container_di.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Notifier {

    @Autowired
    private SpellChecker spellChecker;

    @Autowired
    private MailClient mailClient;

    public boolean send(String text) {
        boolean checkSuccess = this.spellChecker.checkSpell(text);
        if (checkSuccess) {
            return mailClient.sendMail(text.trim().toUpperCase());
        } else
            return false;
    }
}
