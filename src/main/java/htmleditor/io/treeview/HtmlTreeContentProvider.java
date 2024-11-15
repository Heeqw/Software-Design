package htmleditor.io.treeview;

import java.util.Collections;
import java.util.List;

import designpattern.adapter.tree.TreeContentProvider;
import htmleditor.model.HtmlNode;

public class HtmlTreeContentProvider implements TreeContentProvider<HtmlNode> {
    private HtmlNode root;

    public HtmlTreeContentProvider(HtmlNode root) {
        this.root = root;
    }

    @Override
    public List<HtmlNode> getRoots() {
        return Collections.singletonList(root);
    }

    @Override
    public List<HtmlNode> getChildren(HtmlNode parent) {
        return parent.getChildren();
    }
}
