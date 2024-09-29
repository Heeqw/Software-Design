package springframework.abstractfactory.mock;

import springframework.abstractfactory.core.MailClient;
import springframework.abstractfactory.core.NotifierServiceFactory;
import springframework.abstractfactory.core.SpellChecker;

public class MockNotifierServiceFactory implements NotifierServiceFactory {

    private static NotifierServiceFactory instance = new MockNotifierServiceFactory();

    public static NotifierServiceFactory getInstance() {
        return instance;
    }

    @Override
    public MailClient getMailClient() {
        return MailClientMock.getInstance();
    }

    @Override
    public SpellChecker getSpellChecker() {
        return SpellCheckerMock.getInstance();
    }

}
