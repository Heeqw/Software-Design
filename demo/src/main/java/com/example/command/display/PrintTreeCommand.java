package com.example.command.display;

import com.example.command.Command;
import com.example.core.Editor;
import com.example.display.formatter.DisplayFormatter;
import com.example.spellcheck.SpellChecker;

public class PrintTreeCommand implements Command{
    private final Editor editor;
    private final SpellChecker spellChecker;

    public PrintTreeCommand(Editor editor, SpellChecker spellChecker){
        this.editor = editor;
        this.spellChecker = spellChecker;
    }

    @Override
    public void execute() {
        DisplayFormatter formatter = new DisplayFormatter(editor, spellChecker);
        String tree = formatter.formatAsTree();
        System.out.println(tree);
    }

    @Override
    public void undo() {
        
    }

    @Override
    public boolean isReversible() {
        return false;
    }

}
