package springframework.abstractfactory.core;

public class Notifier {

    private NotifierServiceFactory factory;

    public Notifier(NotifierServiceFactory factory) {
        this.factory = factory;
    }

    public boolean send(String text) {
        boolean checkSuccess = factory.getSpellChecker().checkSpell(text);
        if (checkSuccess) {
            return factory.getMailClient().sendMail(text.trim().toUpperCase());
        } else
            return false;
    }
}
