package designpattern.adapter.tree;

import java.util.List;

public interface TreeContentProvider<T> {
    List<T> getRoots();

    List<T> getChildren(T parent);
}
