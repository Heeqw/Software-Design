package springframework.naive;

public class Notifier {
    private SpellChecker spellChecker;
    private MailClient mailClient;

    public Notifier() {
        this.spellChecker = new SpellChecker();
        this.mailClient = new MailClient();
    }

    public boolean send(String text) {
        boolean checkSuccess = this.spellChecker.checkSpell(text);
        if (checkSuccess) {
            return mailClient.sendMail(text.trim().toUpperCase());
        } else
            return false;
    }
}
