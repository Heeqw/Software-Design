package org.heeqw.command.EditCommand;

import org.heeqw.model.HTMLElement;
import org.heeqw.util.IdManager;

public class EditTextCommand extends EditCommand {
    private final String elementId;
    private final String newText;
    public EditTextCommand(String elementId, String newText) {
        this.elementId = elementId;
        this.newText = newText;
    }

    @Override
    public void execute() {
        validateParameters();
        performTextChange();
    }

    private void validateParameters() {
        HTMLElement element = editor.getElementById(elementId);
        if (element == null) {
            System.out.println("Current registered IDs: " + IdManager.getInstance().getAllRegisteredIds());
            throw new IllegalArgumentException("Element not found: " + elementId);
        }
    }

    private void performTextChange() {
        try {
            HTMLElement element = editor.getElementById(elementId);
            element.setText(newText);

        } catch (Exception e) {
            System.err.println("Error during text change operation: " + e.getMessage());
            throw e;
        }
    }
}
