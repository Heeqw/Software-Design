package htmleditor.service.mock;

import htmleditor.service.SpellChecker;

public class SpellCheckImpl implements SpellChecker {

    @Override
    public boolean check(String text) {
        if (text.startsWith("Last")) {
            return false;
        }
        return true;
    }
}
