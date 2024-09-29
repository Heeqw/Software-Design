package springframework.abstractfactory.core;

public interface NotifierServiceFactory {
    MailClient getMailClient();

    SpellChecker getSpellChecker();
}
