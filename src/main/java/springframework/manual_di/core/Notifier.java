package springframework.manual_di.core;

public class Notifier {

    private SpellChecker spellChecker;

    private MailClient mailClient;

    public Notifier(SpellChecker spellChecker, MailClient mailClient) {
        this.spellChecker = spellChecker;
        this.mailClient = mailClient;
    }

    public boolean send(String text) {
        boolean checkSuccess = this.spellChecker.checkSpell(text);
        if (checkSuccess) {
            return mailClient.sendMail(text.trim().toUpperCase());
        } else
            return false;
    }
}
