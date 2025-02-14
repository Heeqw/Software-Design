package org.heeqw.command.DisplayCommand;

import org.heeqw.model.HTMLElement;

import java.util.List;

public class PrintTreeCommand extends DisplayCommand{
    private static final String VERTICAL = "│   ";
    private static final String BRANCH = "├── ";
    private static final String LAST_BRANCH = "└── ";
    private static final String EMPTY = "    ";

    @Override
    public void execute(){
        if (!editor.isInitialized()){
            throw new IllegalStateException("Editor not initialized");
        }
        printTree(editor.getRootElement(), "", true);
    }

    private void printTree(HTMLElement element, String prefix, boolean isLast) {
        // 打印当前元素
        System.out.println(prefix + (isLast ? LAST_BRANCH : BRANCH) + formatElement(element));

//        System.out.println(prefix + (isLast ? LAST_BRANCH : BRANCH) + element.getDisplayName());

        String directText = element.getDirectText().trim();
        List<HTMLElement> children = element.getChildren();

        String newPrefix = prefix + (isLast ? EMPTY : VERTICAL);

        if (!directText.isEmpty()) {
            String textPrefix = newPrefix + (children.isEmpty() ? LAST_BRANCH : BRANCH);
            System.out.println(textPrefix + directText);
        }

        for (int i = 0; i < children.size(); i++) {
            boolean isLastChild = i == children.size() - 1;
            printTree(children.get(i), newPrefix, isLastChild);
        }
    }

    private String formatElement(HTMLElement element){
        if (element.isSpecialTag()){
            return element.getTagName();
        }
        return element.getTagName() + "#" + element.getId();
    }
}
