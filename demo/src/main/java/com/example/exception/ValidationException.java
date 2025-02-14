package com.example.exception;
import java.util.List;
import java.util.ArrayList;

public class ValidationException extends EditorException {
    private final List<ValidationError> errors;

    public ValidationException(String message) {
        super(message);
        this.errors = new ArrayList<>();
    }

    public ValidationException(List<ValidationError> errors) {
        super("Validation failed: " + formatErrors(errors));
        this.errors = errors;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    private static String formatErrors(List<ValidationError> errors) {
        StringBuilder sb = new StringBuilder();
        for (ValidationError error : errors) {
            sb.append("\n- ").append(error.getMessage());
        }
        return sb.toString();
    }
}
