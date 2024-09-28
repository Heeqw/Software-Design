package designpattern.adapter.tree;

import java.util.List;

/**
 * SimpleTreeViewer is a simple implementation of a tree viewer. It uses a
 * TreeContentProvider and a NameProvider to display a tree structure.
 */
public class SimpleTreeViewer<T> {

    private TreeContentProvider<T> contentProvider;
    private NameProvider<T> nameProvider;

    public SimpleTreeViewer(TreeContentProvider<T> contentProvider, NameProvider<T> nameProvider) {
        this.contentProvider = contentProvider;
        this.nameProvider = nameProvider;
    }

    private void printIdent(int ident) {
        for (int i = 0; i < ident; i++) {
            System.out.print("    ");
        }
    }

    void show(T node, int ident) {
        printIdent(ident);
        System.out.println(nameProvider.getName(node));
        List<T> roots = contentProvider.getChildren(node);
        for (T root : roots) {
            show(root, ident + 1);
        }
    }

    // 显示使用空格缩进的树形结构
    public void show() {
        List<T> roots = contentProvider.getRoots();
        for (T root : roots) {
            show(root, 0);
        }
    }

}
