package htmleditor.io.treeview;

import designpattern.adapter.tree.NameProvider;
import htmleditor.model.HtmlNode;

public class HtmlTreeLabelProvider implements NameProvider<HtmlNode> {
    @Override
    public String getName(HtmlNode node) {
        // 输出为下面的格式 h1#title
        return node.getTag().toString() + "#" + node.getId();
    }
}
