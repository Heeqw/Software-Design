package org.heeqw.command.IOCommand;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.heeqw.util.IdManager;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class ReadCommand extends IOCommand{
    private final String filepath;


    public ReadCommand(String filepath){
        this.filepath = filepath;
    }

    @Override
    public void execute() {
        try {
            File file = new File(filepath);
            if (!file.exists()) {
                throw new IOException("File not found: " + filepath);
            }
            if (!file.canRead()) {
                throw new IOException("Cannot read file: " + filepath);
            }

            Document doc = Jsoup.parse(file, StandardCharsets.UTF_8.name());
            IdManager.getInstance().clear();

            doc.getAllElements().forEach(element -> {
                String id = element.id();
                if (!id.isEmpty()){
                    IdManager.getInstance().registerId(id);
                }
            });

            editor.setDocument(doc);


        } catch (Exception e){
            throw new RuntimeException("Failed to read HTML file: " + e.getMessage(), e);
        }
    }
}
