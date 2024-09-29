package springframework.abstractfactory.product;

import springframework.abstractfactory.core.SpellChecker;

public class SpellCheckerImpl implements SpellChecker {
    private static SpellChecker instance = new SpellCheckerImpl();

    public static SpellChecker getInstance() {
        return instance;
    }

    @Override
    public boolean checkSpell(String text) {
        // call spell check api to perform spell check
        throw new UnsupportedOperationException("Unimplemented method 'checkSpell'");
    }

}
