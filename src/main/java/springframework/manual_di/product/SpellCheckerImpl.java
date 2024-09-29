package springframework.manual_di.product;

import springframework.manual_di.core.SpellChecker;

public class SpellCheckerImpl implements SpellChecker {

    @Override
    public boolean checkSpell(String text) {
        // call spell check api to perform spell check
        throw new UnsupportedOperationException("Unimplemented method 'checkSpell'");
    }

}
