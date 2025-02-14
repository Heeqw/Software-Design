package com.example.display.visitor;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

public interface  DocumentVisitor {
    void visit(Element element, int depth);
    void visit(TextNode textNode, int depth);
    String getOutput();
}
