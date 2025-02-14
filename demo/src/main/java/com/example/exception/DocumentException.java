package com.example.exception;

public class DocumentException extends EditorException {
    private final String elementId;

    public DocumentException(String elementId, String message) {
        super(String.format("Document operation failed for element '%s': %s", elementId, message));
        this.elementId = elementId;
    }

    public DocumentException(String elementId, String message, Throwable cause) {
        super(String.format("Document operation failed for element '%s': %s", elementId, message), cause);
        this.elementId = elementId;
    }

    public String getElementId() {
        return elementId;
    }
    
}
