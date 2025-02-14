package com.example.command.edit;

import com.example.command.Command;
import com.example.core.Editor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.example.exception.CommandException;


public class EditTextCommand implements Command{
    private final Editor editor;
    private final String elementId;
    private final String newText;
    private String oldText;
    private Element editedElement;

    public EditTextCommand(Editor editor, String elementId, String newText) {
        this.editor = editor;
        this.elementId = elementId;
        this.newText = newText;
    }

    @Override
    public void execute() {
        Document document = editor.getDocument();
        editedElement = document.getElementById(elementId);

        if (editedElement == null) {
            throw new CommandException("EditText", "Element not found");
        }

        oldText = editedElement.text();
        editedElement.text(newText);
        editor.markModified();
    }

    @Override
    public void undo() {
        if (editedElement != null && oldText != null) {
            editedElement.text(oldText);
            editor.markModified();
        }
    }

    @Override
    public boolean isReversible() {
        return true;
    }
}
