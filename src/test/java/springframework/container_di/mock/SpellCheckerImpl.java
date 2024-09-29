package springframework.container_di.mock;

import org.springframework.stereotype.Component;

import springframework.container_di.core.SpellChecker;

@Component
public class SpellCheckerImpl implements SpellChecker {
    public SpellCheckerImpl() {
    }

    @Override
    public boolean checkSpell(String text) {
        return true;
    }

}
