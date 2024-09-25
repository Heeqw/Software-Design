package designpattern.adapter.tree;

import java.util.List;

interface Visitor<T> {
    void visit(Node<T> node);
}

class Node<T> {
    T data;
    List<Boolean> hasNextSlibling;

    public Node(T data, List<Boolean> hasNextSlibling) {
        this.data = data;
        this.hasNextSlibling = hasNextSlibling;
    }

    @Override
    public String toString() {

        return "Node{" +
                "data=" + data +
                ", hasNextSlibling=" + hasNextSlibling +
                '}';

    }
}