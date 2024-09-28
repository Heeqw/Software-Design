package designpattern.adapter.livedemo;

import java.util.ArrayList;
import java.io.File;
import java.util.List;
import java.util.Arrays;

// Target
interface TreeContentProvider<T> {
    List<T> getChildren(T parent);

    List<T> getRoots();

    String getName(T node);
}

// Client
public class TreeViewer<T> {

    private TreeContentProvider<T> contentProvider;

    public TreeViewer(TreeContentProvider<T> contentProvider) {
        this.contentProvider = contentProvider;
    }

    private void displayNode(T node, int level) {
        String name = contentProvider.getName(node);
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
        System.out.println(name);
        List<T> children = contentProvider.getChildren(node);
        for (T child : children) {
            displayNode(child, level + 1);
        }
    }

    public void display() {
        List<T> roots = contentProvider.getRoots();
        for (T root : roots) {
            displayNode(root, 0);
        }
    }

}

// Adapter
class FileTreeContentProvider implements TreeContentProvider<File> {
    private File root;

    public FileTreeContentProvider(File root) {
        this.root = root;
    }

    @Override
    public List<File> getChildren(File parent) {
        ArrayList<File> children = new ArrayList<File>();
        File[] files = parent.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    children.add(file);
                }
            }
        }
        return children;
    }

    @Override
    public List<File> getRoots() {
        return Arrays.asList(root.listFiles());
    }

    @Override
    public String getName(File node) {
        return node.getName();
    }

}
