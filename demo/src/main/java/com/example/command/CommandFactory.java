package com.example.command;

import com.example.command.display.DirIndentCommand;
import com.example.command.display.DirTreeCommand;
import com.example.command.display.PrintIndentCommand;
import com.example.command.display.PrintTreeCommand;
import com.example.command.display.SpellCheckCommand;
import com.example.command.edit.AppendCommand;
import com.example.command.edit.DeleteCommand;
import com.example.command.edit.EditIdCommand;
import com.example.command.edit.EditTextCommand;
import com.example.command.edit.InsertCommand;
import com.example.command.session.CloseCommand;
import com.example.command.session.LoadCommand;
import com.example.command.session.SaveCommand;
import com.example.command.session.ShowIdCommand;
import com.example.config.Configuration;
import com.example.config.ConfigurationManager;
import com.example.core.Editor;
import com.example.core.SessionManager;
import com.example.spellcheck.LanguageToolSpellChecker;
import com.example.spellcheck.SpellChecker;

public class CommandFactory {
    private final SessionManager sessionManager;
    private final Configuration config;
    private final SpellChecker spellChecker;

    public CommandFactory() {
        this.sessionManager = SessionManager.getInstance();
        this.config = ConfigurationManager.getInstance().getConfiguration();
        this.spellChecker = new LanguageToolSpellChecker();
    }

    // 编辑类命令
    public Command createInsertCommand(Editor editor, String tagName, String idValue, 
                                     String insertLocation, String textContent) {
        validateEditor(editor);
        return new InsertCommand(editor, tagName, idValue, insertLocation, textContent);
    }

    public Command createAppendCommand(Editor editor, String tagName, String idValue, 
                                     String parentElement, String textContent) {
        validateEditor(editor);
        return new AppendCommand(editor, tagName, idValue, parentElement, textContent);
    }

    public Command createEditIdCommand(Editor editor, String oldId, String newId) {
        validateEditor(editor);
        return new EditIdCommand(editor, oldId, newId);
    }

    public Command createEditTextCommand(Editor editor, String elementId, String newText) {
        validateEditor(editor);
        return new EditTextCommand(editor, elementId, newText);
    }

    public Command createDeleteCommand(Editor editor, String elementId) {
        validateEditor(editor);
        return new DeleteCommand(editor, elementId);
    }

    // 显示类命令
    public Command createPrintTreeCommand(Editor editor) {
        validateEditor(editor);
        return new PrintTreeCommand(editor, spellChecker);
    }

    public Command createPrintIndentCommand(Editor editor, int indentSize) {
        validateEditor(editor);
        return new PrintIndentCommand(editor, indentSize, spellChecker);
    }

    public Command createSpellCheckCommand(Editor editor) {
        validateEditor(editor);
        return new SpellCheckCommand(editor, spellChecker);
    }

    // 目录显示命令
    public Command createDirTreeCommand() {
        return new DirTreeCommand(sessionManager);
    }

    public Command createDirIndentCommand(int indentSize) {
        return new DirIndentCommand(sessionManager, indentSize);
    }

    // 会话命令
    public Command createLoadCommand(String filepath) {
        return new LoadCommand(sessionManager, filepath);
    }

    public Command createSaveCommand(Editor editor) {
        validateEditor(editor);
        return new SaveCommand(editor);
    }

    public Command createCloseCommand(Editor editor) {
        validateEditor(editor);
        return new CloseCommand(sessionManager, editor);
    }

    public Command createShowIdCommand(Editor editor, boolean show) {
        validateEditor(editor);
        return new ShowIdCommand(editor, show);
    }

    private void validateEditor(Editor editor) {
        if (editor == null) {
            throw new IllegalArgumentException("Editor cannot be null");
        }
    }
}
