package com.example.command.edit;

import com.example.command.Command;
import com.example.core.Editor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.example.exception.CommandException;

public class EditIdCommand implements Command {
    private final Editor editor;
    private final String oldId;
    private final String newId;
    private Element editedElement;

    public EditIdCommand(Editor editor, String oldId, String newId) {
        this.editor = editor;
        this.oldId = oldId;
        this.newId = newId;
    }

    @Override
    public void execute() {
        Document document = editor.getDocument();
        editedElement = document.getElementById(oldId);

        if (editedElement == null) {
            throw new CommandException("EditId", "Element not found");
        }

        if (document.getElementById(newId) != null) {
            throw new CommandException("EditId", "Element with the same ID already exists");
        }

        editedElement.attr("id", newId);
        editor.markModified();
    }

    @Override
    public void undo() {
        if (editedElement != null) {    
            editedElement.attr("id", oldId);
            editor.markModified();
        }
    }

    @Override
    public boolean isReversible() {
        return true;
    }
}

