package com.example.command.edit;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.example.command.Command;
import com.example.core.Editor;
import com.example.exception.CommandException;

public class DeleteCommand implements Command {
    private final Editor editor;
    private final String targetId;
    private Element deletedElement;
    private Element parentElement;
    private int originalIndex;

    public DeleteCommand(Editor editor, String targetId) {
        this.editor = editor;
        this.targetId = targetId;
    }

    @Override
    public void execute() {
        Document doc = editor.getDocument();
        Element target = doc.getElementById(targetId);
        if (target == null) {
            throw new CommandException(getCommandName(), "Element not found: " + targetId);
        }

        // 保存状态用于撤销
        parentElement = target.parent();
        originalIndex = target.elementSiblingIndex();
        deletedElement = target.clone();  // 深度克隆以保留所有内容

        // 执行删除
        target.remove();
        editor.markModified();
    }

    @Override
    public void undo() {
        try {
            // 在原始位置恢复元素
            if (originalIndex >= parentElement.children().size()) {
                parentElement.appendChild(deletedElement.clone());
            } else {
                parentElement.children().get(originalIndex).before(deletedElement.clone());
            }
            editor.markModified();
        } catch (Exception e) {
            throw new CommandException(getCommandName(), 
                "Failed to undo delete command: " + e.getMessage());
        }
    }

    @Override
    public boolean isReversible() {
        return true;
    }
}
