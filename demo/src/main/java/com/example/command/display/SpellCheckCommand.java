package com.example.command.display;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

import com.example.command.Command;
import com.example.core.Editor;
import com.example.spellcheck.SpellChecker;
import com.example.spellcheck.SpellError;

public class SpellCheckCommand implements Command {
    private final Editor editor;
    private final SpellChecker spellChecker;

    public SpellCheckCommand(Editor editor, SpellChecker spellChecker){
        this.editor = editor;
        this.spellChecker = spellChecker;
    }

    @Override
    public void execute() {
       Document document = editor.getDocument();
       List<SpellError> allErrors = new ArrayList<>();
       
       // 检查所有元素
       checkElement(document.body(), allErrors);
        if(allErrors.isEmpty()){
           System.out.println("No spelling errors found.");
       } else {
           System.out.println("Spelling errors found:");
           for (SpellError error: allErrors) {
               System.out.printf("- %s (Suggestions: %s)\n",
                   error.getWord(), 
                   String.join(", ", error.getSuggestions())
               );
           }
       }
   }
    private void checkElement(Element element, List<SpellError> errors) {
       // 检查元素自身的文本
       if (!element.ownText().trim().isEmpty() && spellChecker.hasError(element.ownText())) {
           errors.addAll(spellChecker.check(element.ownText()));
       }
        // 检查文本节点
       for (TextNode node : element.textNodes()) {
           String text = node.text().trim();
           if (!text.isEmpty() && spellChecker.hasError(text)) {
               errors.addAll(spellChecker.check(text));
           }
       }
        // 递归检查子元素
       for (Element child : element.children()) {
           checkElement(child, errors);
       }
   }

    @Override
    public void undo() {
        
    }

    @Override
    public boolean isReversible() {
        return false;
    }

}
