package com.example.display.visitor;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

import com.example.spellcheck.SpellChecker;

public class TreeDisplayVisitor implements DocumentVisitor {
    private final StringBuilder output = new StringBuilder();
    private final boolean showId;
    private final SpellChecker spellChecker;
    private static final String VERTICAL = "│   ";
    private static final String CORNER = "└── ";
    private static final String TEE = "├── ";
    private static final String SPACE = "    ";

    public TreeDisplayVisitor(boolean showId, SpellChecker spellChecker) {
        this.showId = showId;
        this.spellChecker = spellChecker;
    }

    @Override
    public void visit(Element element, int depth) {
        String prefix = getPrefix(element, depth);
        appendElementStart(element, prefix);
        visitChildren(element, depth);
    }

    private void appendElementStart(Element element, String prefix) {
        output.append(prefix);
        if (spellChecker.hasError(element)) {
            output.append("[X] ");
        }
        output.append("<").append(element.tagName());
        
        if (showId && element.hasAttr("id")) {
            output.append(" id=\"").append(element.attr("id")).append("\"");
        }
        output.append(">");
        
        String ownText = element.ownText().trim();
        if (!ownText.isEmpty()) {
            output.append(" ").append(ownText);
        }
        output.append("\n");
    }

    private String getPrefix(Element element, int depth) {
        if (depth == 0) return "";
        return SPACE.repeat(depth) + CORNER;
    }

    private void visitChildren(Element element, int depth) {
        List<Element> children = element.children();
        for (Element child : children) {
            visit(child, depth + 1);
        }
    }

    @Override
    public void visit(TextNode textNode, int depth) {
        String text = textNode.text().trim();
        if (!text.isEmpty()) {
            String prefix = SPACE.repeat(depth);
            output.append(prefix)
                  .append(CORNER)
                  .append("\"")
                  .append(text)
                  .append("\"");
            
            if (spellChecker.hasError(textNode.text())) {
                output.append(" [X]");
            }
            output.append("\n");
        }
    }

    @Override
    public String getOutput() {
        return output.toString();
    }
}
