package com.example.filesystem.visitor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.example.filesystem.FileSystemNode;
import com.example.filesystem.HtmlFileNode;

public class HtmlNodeVisitor implements  FileSystemVisitor{
    private final StringBuilder output = new StringBuilder();
    private final boolean showId;

    public HtmlNodeVisitor(boolean showId) {
        this.showId = showId;
    }

    @Override
    public void visit(FileSystemNode node) {
        if (node instanceof HtmlFileNode) {
            HtmlFileNode htmlNode = (HtmlFileNode) node;
            Document doc = htmlNode.getHtmlDocument();
            output.append("File: ").append(node.getName()).append("\n");
            visitElement(doc.root(), 0);
        } else if (node.isDirectory()) {
            output.append("Directory: ").append(node.getName()).append("\n");
            for (FileSystemNode child : node.getChildren()) {
                visit(child);
            }
        }
    }

    private void visitElement(Element element, int depth) {
        String indent = "  ".repeat(depth);
        output.append(indent).append("<").append(element.tagName());
        
        if (showId && element.hasAttr("id")) {
            output.append(" id=\"").append(element.attr("id")).append("\"");
        }
        output.append(">\n");

        // 处理文本内容
        String text = element.ownText().trim();
        if (!text.isEmpty()) {
            output.append(indent).append("  ").append(text).append("\n");
        }

        // 递归处理子元素
        for (Element child : element.children()) {
            visitElement(child, depth + 1);
        }
    }

    @Override
    public void afterVisit(FileSystemNode node) {
        // 由于我们在visit方法中已经处理了所有的递归逻辑
        // 这里不需要特别的实现
    }

    @Override
    public String getOutput() {
        return output.toString();
    }
}
