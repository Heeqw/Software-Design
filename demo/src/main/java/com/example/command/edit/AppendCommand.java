package com.example.command.edit;

import com.example.command.Command;
import com.example.core.Editor;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import com.example.exception.CommandException;

public class AppendCommand implements Command {
    private final Editor editor;
    private final String tagName;
    private final String idValue;
    private final String parentElement;
    private final String textContent;
    private Element appendedElement;

    public AppendCommand(Editor editor, String tagName, String idValue, String parentElement, String textContent) {
        this.editor = editor;
        this.tagName = tagName;
        this.idValue = idValue;
        this.parentElement = parentElement;
        this.textContent = textContent;
    }

    @Override
    public void execute() {
        Document document = editor.getDocument();
        Element parent = document.getElementById(parentElement);

        if (parent == null) {
            throw new CommandException("Append", "Parent element not found");
        }

        if (document.getElementById(idValue) != null) {
            throw new CommandException("Append", "Element with the same ID already exists");
        }

        Element newElement = document.createElement(tagName);
        newElement.attr("id", idValue);
        if (textContent != null) {
            newElement.text(textContent);
        }
        parent.appendChild(newElement);
        appendedElement = newElement;
        editor.markModified();
    }

    @Override
    public void undo() {
        if (appendedElement != null) {
            appendedElement.remove();
            editor.markModified();
        }
    }

    @Override
    public boolean isReversible() {
        return true;
    }
}
