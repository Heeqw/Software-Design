package htmleditor.model;

import java.util.Iterator;

public class HtmlDocument {
    private HtmlNode root;

    public HtmlDocument() {
        this.root = new HtmlNode("HTML", "root", "", null);
    }

    // 添加节点
    public void addItem(String parent, String tag, String id, String content) {
        HtmlNode parentNode = findByID(parent);
        if (parentNode == null)
            throw new IllegalArgumentException("Parent node not found");
        parentNode.createChild(tag, id, content);
    }

    // 删除节点
    public HtmlNode deleteItem(String id) {
        HtmlNode node = findByID(id);
        if (node == null)
            throw new IllegalArgumentException("Node not found");
        node.getParent().children.remove(node);
        return node;
    }

    // 根据id查找节点
    public HtmlNode findByID(String id) {
        Iterator<HtmlNode> iterator = root.iteratorAll();
        while (iterator.hasNext()) {
            HtmlNode node = iterator.next();
            if (node.id.equals(id))
                return node;
        }
        return null;
    }

    public HtmlNode getRoot() {
        return root;
    }
}
