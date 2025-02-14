package com.example.command.edit;
import com.example.command.Command;
import com.example.core.Editor;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import com.example.exception.CommandException;

public class InsertCommand implements Command {
    private final Editor editor;
    private final String tagName;
    private final String idValue;
    private final String insertLocation;
    private final String textContent;
    private Element insertedElement;

    public InsertCommand(Editor editor, String tagName, String idValue, String insertLocation, String testContent) {
        this.editor = editor;
        this.tagName = tagName;
        this.idValue = idValue;
        this.insertLocation = insertLocation;
        this.textContent = testContent;
    }

    @Override
    public void execute() {
        Document document = editor.getDocument();
        Element target = document.getElementById(insertLocation);

        if (target == null) {
            throw new CommandException("Insert", "Target element not found");
        }

        if (document.getElementById(idValue) != null) {
            throw new CommandException("Insert", "Element with the same ID already exists");
        }

        Element newElement = document.createElement(tagName);
        newElement.attr("id", idValue);
        if (textContent != null) {
            newElement.text(textContent);
        }

        target.before(newElement);
        insertedElement = newElement;
        editor.markModified();
    }

    @Override
    public void undo() {
        if (insertedElement != null) {
            insertedElement.remove();
            editor.markModified();
        }
    }

    @Override
    public boolean isReversible() {
        return true;
    }
}
