package htmleditor.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HtmlNode {

    String tag;
    String id;
    String content;

    List<HtmlNode> children = new ArrayList<>();
    HtmlNode parent;

    public HtmlNode(String tag, String id, String content, HtmlNode parent) {
        this.tag = tag;
        this.id = id;
        this.content = content;
        this.parent = parent;
    }

    public String getTag() {
        return tag;
    }

    public List<HtmlNode> getChildren() {
        return children;
    }

    public String getContent() {
        return content;
    }

    public HtmlNode getParent() {
        return parent;
    }

    public HtmlNode createChild(String tag, String id, String content) {
        return insertChildAt(children.size(), tag, id, content);
    }

    public HtmlNode insertChildAt(int index, String tag, String id, String content) {
        HtmlNode child = new HtmlNode(tag, id, content, this);
        this.children.add(index, child);
        return child;
    }

    public String getId() {
        return id;
    }

    // 使用深度优先遍历的迭代器
    public Iterator<HtmlNode> iteratorAll() {
        return new Iterator<HtmlNode>() {
            private final List<HtmlNode> stack = new ArrayList<>();
            private boolean isFirst = true;

            @Override
            public boolean hasNext() {
                if (isFirst) {
                    return true;
                }
                return !stack.isEmpty();
            }

            @Override
            public HtmlNode next() {
                if (isFirst) {
                    isFirst = false;
                    stack.addAll(children);
                    return HtmlNode.this;
                }

                HtmlNode current = stack.remove(stack.size() - 1);
                stack.addAll(current.children);
                return current;
            }
        };
    }

    // 获取子节点在children列表中的索引
    public int indexOf(HtmlNode node) {
        return children.indexOf(node);
    }

}