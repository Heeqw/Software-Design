package designpattern.adapter.tree.demo;

import java.util.Arrays;
import java.util.List;

import designpattern.adapter.tree.SimpleTreeViewer;
import designpattern.adapter.tree.TreeContentProvider;
import designpattern.adapter.tree.VisualTreeVIewer;

public class SimpleTree {

    public static void main(String[] args) {

        TreeContentProvider<Integer> contentProvider = new TreeContentProvider<Integer>() {
            @Override
            public List<Integer> getRoots() {
                return Arrays.asList(1, 2, 3);
            }

            @Override
            public List<Integer> getChildren(Integer parent) {
                if (parent == 2) {
                    return Arrays.asList(4, 5, 6);
                } else if (parent == 5) {
                    return Arrays.asList(7);
                } else {
                    return List.of();
                }
            }
        };

        new SimpleTreeViewer<Integer>(
                contentProvider,
                (node) -> String.valueOf(node)).show();
        new VisualTreeVIewer<Integer>(
                contentProvider,
                (node) -> String.valueOf(node)).show();

    }
}
