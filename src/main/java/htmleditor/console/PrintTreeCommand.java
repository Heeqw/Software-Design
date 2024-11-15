package htmleditor.console;

import designpattern.adapter.tree.NameProvider;
import designpattern.adapter.tree.VisualTreeViewer;
import htmleditor.command.Command;
import htmleditor.io.treeview.HtmlTreeContentProvider;
import htmleditor.io.treeview.HtmlTreeLabelProvider;
import htmleditor.model.HtmlDocument;
import htmleditor.model.HtmlNode;
import htmleditor.service.SpellChecker;

/**
 * 拼写检查的装饰器
 */
class SpellCheckNameProvider implements NameProvider<HtmlNode> {

    private SpellChecker spellChecker;
    private NameProvider<HtmlNode> nameProvider;

    public SpellCheckNameProvider(SpellChecker spellChecker, NameProvider<HtmlNode> nameProvider) {
        this.spellChecker = spellChecker;
        this.nameProvider = nameProvider;
    }

    @Override
    public String getName(HtmlNode node) {
        return spellChecker.check(node.getContent()) ? nameProvider.getName(node)
                : "[X]" + nameProvider.getName(node);
    }
}

class ShowIdNameProvider implements NameProvider<HtmlNode> {
    private NameProvider<HtmlNode> nameProvider;

    public ShowIdNameProvider(NameProvider<HtmlNode> nameProvider) {
        this.nameProvider = nameProvider;
    }

    @Override
    public String getName(HtmlNode node) {
        return nameProvider.getName(node) + "(" + node.getId() + ")";
    }
}

public class PrintTreeCommand implements Command {
    private HtmlDocument document;
    private SpellChecker spellChecker;

    public PrintTreeCommand(HtmlDocument document, SpellChecker spellChecker) {
        this.document = document;
        this.spellChecker = spellChecker;
    }

    @Override
    public void execute() {
        VisualTreeViewer<HtmlNode> viewer = new VisualTreeViewer<>(new HtmlTreeContentProvider(document.getRoot()),
                new ShowIdNameProvider(new SpellCheckNameProvider(spellChecker, new HtmlTreeLabelProvider())));
        viewer.show();
    }
}
