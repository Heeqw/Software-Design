package springframework.abstractfactory.product;

import springframework.abstractfactory.core.MailClient;
import springframework.abstractfactory.core.NotifierServiceFactory;
import springframework.abstractfactory.core.SpellChecker;

public class NotifierServiceFactoryImpl implements NotifierServiceFactory {

    @Override
    public MailClient getMailClient() {
        return MailClientImpl.getInstance();
    }

    @Override
    public SpellChecker getSpellChecker() {
        return SpellCheckerImpl.getInstance();
    }

}
