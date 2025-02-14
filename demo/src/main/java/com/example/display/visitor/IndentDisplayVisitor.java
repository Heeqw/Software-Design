package com.example.display.visitor;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

import com.example.spellcheck.SpellChecker;

public class IndentDisplayVisitor implements DocumentVisitor {
    private final StringBuilder output = new StringBuilder();
    private final int indentSize;
    private final SpellChecker spellChecker;
    private final boolean showId;

    public IndentDisplayVisitor(int indentSize, SpellChecker spellChecker, boolean showId) {
        this.indentSize = indentSize;
        this.spellChecker = spellChecker;
        this.showId = showId;
    }

    @Override
    public void visit(Element element, int depth) {
        String indent = " ".repeat(depth * indentSize);
        
        // 添加开始标签
        output.append(indent);
        if (spellChecker.hasError(element)) {
            output.append("[X] ");
        }
        output.append("<").append(element.tagName());
        if (showId && element.hasAttr("id")) {
            output.append(" id=\"").append(element.attr("id")).append("\"");
        }
        output.append(">");

        // 添加元素的文本内容
        String ownText = element.ownText().trim();
        if (!ownText.isEmpty()) {
            output.append(" ").append(ownText);
        }

        // 检查是否需要换行
        boolean hasChildren = !element.children().isEmpty();
        boolean shouldBreakLine = hasChildren || element.html().contains("\n");
        
        if (shouldBreakLine) {
            output.append("\n");
            // 处理子元素
            element.children().forEach(child -> visit(child, depth + 1));
            // 结束标签新起一行
            output.append(indent);
        }

        // 添加结束标签
        output.append("</").append(element.tagName()).append(">");
        
        // 最后的换行
        output.append("\n");
    }

    @Override
    public void visit(TextNode textNode, int depth) {
        String text = textNode.text().trim();
        if (!text.isEmpty()) {
            String indent = " ".repeat(depth * indentSize);
            output.append(indent)
                  .append("\"")
                  .append(text)
                  .append("\"");
            
            if (spellChecker.hasError(text)) {
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
