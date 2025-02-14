package com.example.command.display;

import com.example.command.Command;
import com.example.core.Editor;
import com.example.display.formatter.DisplayFormatter;
import com.example.spellcheck.SpellChecker;

public class PrintIndentCommand implements Command{
    private final Editor editor;
    private final int indentSize;
    private final SpellChecker spellChecker;

    public PrintIndentCommand(Editor editor, int indentSize, SpellChecker spellChecker){
        this.editor = editor;
        this.indentSize = indentSize;
        this.spellChecker = spellChecker;
    }

    @Override
    public void execute(){
        DisplayFormatter formatter = new DisplayFormatter(editor,spellChecker);
        String result = formatter.formatAsIndent(indentSize);
        System.out.println(result);
    }

    @Override
    public void undo(){

    }

    @Override
    public boolean isReversible() {
        return false;
    }
}
