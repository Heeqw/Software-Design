package org.heeqw.command.IOCommand;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;

public class SaveCommand extends IOCommand{
    private final String filepath;

    public SaveCommand(String filepath){
        this.filepath = filepath;
    }

    @Override
    public void execute(){
        if (!editor.isInitialized()){
            throw new IllegalStateException("Editor not initialized");
        }

        try {
            File file = new File(filepath);

            File parent = file.getParentFile();
            if (parent != null && !parent.exists()){
                if (!parent.mkdirs()){
                    throw new RuntimeException("Failed to create directories for: " + filepath);
                }
            }

            try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)){
                writer.write(editor.getJsoupDocument().html());
            }
        }
        catch (Exception e){
            throw new RuntimeException("Failed to save HTML file: " + e.getMessage(), e);
        }
    }
}
