package springframework.abstractfactory.mock;

import springframework.abstractfactory.core.SpellChecker;

public class SpellCheckerMock implements SpellChecker {

    private static SpellChecker instance = new SpellCheckerMock();

    public static SpellChecker getInstance() {
        return instance;
    }

    public SpellCheckerMock() {
    }

    @Override
    public boolean checkSpell(String text) {
        return true;
    }
}
