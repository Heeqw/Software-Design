package springframework.livedemo.main;

import org.springframework.stereotype.Service;

import springframework.livedemo.core.SpellChecker;

@Service
public class SpellCheckerImpl implements SpellChecker {

    @Override
    public boolean checkSpell(String text) {
        // call spell check api to perform spell check
        return true;
    }

}
