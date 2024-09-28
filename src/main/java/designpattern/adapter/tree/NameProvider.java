package designpattern.adapter.tree;

/**
 * NameProvider is an interface that provides the name of a node in a tree.
 */
public interface NameProvider<T> {
    String getName(T node);

}
