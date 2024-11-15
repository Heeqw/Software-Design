package htmleditor.console;

import htmleditor.command.Command;
import htmleditor.model.HtmlDocument;
import htmleditor.service.SpellChecker;

public class CheckSpellCommand implements Command {
    private HtmlDocument document;
    private SpellChecker spellChecker;

    public CheckSpellCommand(HtmlDocument document, SpellChecker spellChecker) {
        this.document = document;
        this.spellChecker = spellChecker;
    }

    @Override
    public void execute() {
        document.getRoot().iteratorAll().forEachRemaining(node -> {
            if (!spellChecker.check(node.getContent())) {
                System.out.println("Spell check failed for node: " + node.getId());
            }
        });
    }
}
