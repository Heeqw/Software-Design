package designpattern.adapter.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VisualTreeVIewer<T> {

    private TreeContentProvider<T> contentProvider;
    private NameProvider<T> nameProvider;

    public VisualTreeVIewer(TreeContentProvider<T> contentProvider, NameProvider<T> nameProvider) {
        this.contentProvider = contentProvider;
        this.nameProvider = nameProvider;
    }

    void visitIterator(Iterator<T> iterator, List<Boolean> hasNextSlibling, Visitor<T> visitor) {
        while (iterator.hasNext()) {
            // 复制一个列表
            ArrayList<Boolean> newNext = new ArrayList<>();
            newNext.addAll(hasNextSlibling);

            T node = iterator.next();
            if (!iterator.hasNext())
                newNext.add(Boolean.FALSE);
            else
                newNext.add(Boolean.TRUE);
            visit0(node, newNext, visitor);
        }
    }

    void visit0(T node, List<Boolean> hasNextSlibling, Visitor<T> visitor) {
        visitor.visit(new Node<T>(node, hasNextSlibling));
        visitIterator(contentProvider.getChildren(node).iterator(), hasNextSlibling, visitor);
    }

    void visit(Visitor<T> visitor) {
        visitIterator(contentProvider.getRoots().iterator(), List.of(), visitor);
    }

    // 显示使用表格线构造的树形结构
    public void show() {
        visit(new Visitor<T>() {
            @Override
            public void visit(Node<T> node) {
                Iterator<Boolean> iterator = node.hasNextSlibling.iterator();
                while (iterator.hasNext()) {
                    Boolean hasNextSlibling = iterator.next();
                    if (!iterator.hasNext()) {
                        if (hasNextSlibling)
                            System.out.print("├── ");
                        else
                            System.out.print("└── ");
                    } else {
                        if (hasNextSlibling)
                            System.out.print("│   ");
                        else
                            System.out.print("    ");
                    }
                }
                System.out.println(nameProvider.getName(node.data));
            }
        });
    }
}
