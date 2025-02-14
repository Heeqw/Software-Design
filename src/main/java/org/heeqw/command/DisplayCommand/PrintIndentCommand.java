package org.heeqw.command.DisplayCommand;

import org.heeqw.model.HTMLElement;

import java.util.List;

public class PrintIndentCommand extends DisplayCommand{
    private final int indentSize;

    public PrintIndentCommand(int indentSize){
        this.indentSize = indentSize;
    }

    @Override
    public void execute(){
        if (!editor.isInitialized()){
            throw new IllegalStateException("Editor not initialized");
        }
        System.out.println(formatElement(editor.getRootElement(), 0));
    }

    public String formatElement(HTMLElement element, int level){
        StringBuilder result = new StringBuilder();

        String indent = " ".repeat(level * indentSize);

        result.append(indent).append("<").append(element.getTagName());
        if (!element.isSpecialTag()){
            result.append(" id=\"").append(element.getId()).append("\"");
        }
        result.append(">");

//        result.append(indent)
//                .append("<")
//                .append(element.getDisplayName())
//                .append(">");

        String directText = element.getDirectText();
        if (!directText.isEmpty()){
            result.append("\n").append(indent).append(" ".repeat(indentSize))
                    .append(directText);
        }

        List<HTMLElement> children = element.getChildren();
        if (!children.isEmpty()){
            for (HTMLElement child: children){
                result.append("\n").append(formatElement(child, level + 1));
            }
            result.append("\n").append(indent);
        }

        result.append("</").append(element.getTagName()).append(">");

        return result.toString();
    }
}
