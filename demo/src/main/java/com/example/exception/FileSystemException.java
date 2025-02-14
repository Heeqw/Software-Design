package com.example.exception;
import java.nio.file.Path;  

public class FileSystemException extends EditorException {
    private final Path path;

    public FileSystemException(Path path, String message) {
        super(String.format("File system operation failed for '%s': %s", path, message));
        this.path = path;
    }

    public FileSystemException(Path path, String message, Throwable cause) {
        super(String.format("File system operation failed for '%s': %s", path, message), cause);
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    
}
