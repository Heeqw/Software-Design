package com.example.display.formatter;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.example.core.Editor;
import com.example.display.visitor.DocumentVisitor;
import com.example.display.visitor.IndentDisplayVisitor;
import com.example.display.visitor.TreeDisplayVisitor;
import com.example.spellcheck.SpellChecker;

public class DisplayFormatter {
    private final Editor editor;
    private final SpellChecker spellChecker;

    public DisplayFormatter(Editor editor, SpellChecker spellChecker) {
        this.editor = editor;
        this.spellChecker = spellChecker;
    }

    public String formatAsTree(){
        Document document = editor.getDocument();
        DocumentVisitor visitor = new TreeDisplayVisitor(editor.isShowId(), spellChecker);

        traverseDocument(document, visitor);
        return visitor.getOutput();
    }

    public String formatAsIndent(int indentSize){
        Document document = editor.getDocument();
        DocumentVisitor visitor = new IndentDisplayVisitor(indentSize, spellChecker, editor.isShowId());

        traverseDocument(document, visitor);
        return visitor.getOutput();
    }

    private void traverseDocument(Document document, DocumentVisitor visitor) {
        Element html = document.root();
        // Element head = document.head();
        // Element body = document.body();

        visitor.visit(html, 0);
        // if (head != null) {
        //     visitor.visit(head, 1);
        // }
        // if (body != null) {
        //     visitor.visit(body, 1);
        // }
    }
}
