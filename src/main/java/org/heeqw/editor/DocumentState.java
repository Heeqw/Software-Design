package org.heeqw.editor;

import org.jsoup.nodes.Document;

import java.util.HashSet;
import java.util.Set;

public class DocumentState {
    private final Document document;
    private final Set<String> registeredIds;


    public DocumentState(Document document, Set<String> registeredIds) {
        this.document = document.clone();
        this.registeredIds = new HashSet<>(registeredIds);
    }



    public Document getDocument() {
        return document;
    }

    public Set<String> getRegisteredIds() {
        return registeredIds;
    }
}
