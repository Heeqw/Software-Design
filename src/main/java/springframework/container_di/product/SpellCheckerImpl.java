package springframework.container_di.product;

import org.springframework.stereotype.Component;

import springframework.container_di.core.SpellChecker;

@Component
public class SpellCheckerImpl implements SpellChecker {

    @Override
    public boolean checkSpell(String text) {
        // call spell check api to perform spell check
        return true;
    }

}
