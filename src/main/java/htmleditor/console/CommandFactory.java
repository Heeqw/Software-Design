package htmleditor.console;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import htmleditor.command.Command;
import htmleditor.command.CommandManager;
import htmleditor.command.InsertTagCommand;
import htmleditor.command.RedoCommand;
import htmleditor.command.UndoCommand;
import htmleditor.model.HtmlDocument;
import htmleditor.service.SpellChecker;

public class CommandFactory {
    private static final Pattern APPEND = Pattern.compile("append\\s+(\\w+)\\s+(\\w+)\\s+(\\w+)\\s+'([^']*)'");

    public static Command parseAppendCommand(HtmlDocument document, String input) {

        Matcher matcher = APPEND.matcher(input);
        if (matcher.matches()) {
            return new InsertTagCommand(document, matcher.group(1), matcher.group(2), matcher.group(3),
                    matcher.group(4));
        }
        return null;
    }

    private SpellChecker spellChecker;

    public CommandFactory(SpellChecker spellChecker) {
        this.spellChecker = spellChecker;
    }

    public Command createCommand(CommandManager commandManager, HtmlDocument document, String input) {
        if (input.startsWith("append")) {
            return parseAppendCommand(document, input);
        } else if (input.startsWith("undo")) {
            return new UndoCommand(commandManager);
        } else if (input.startsWith("redo")) {
            return new RedoCommand(commandManager);
        } else if (input.startsWith("print-tree")) {
            return new PrintTreeCommand(document, spellChecker);
        } else if (input.startsWith("check-spell")) {
            return new CheckSpellCommand(document, spellChecker);
        }
        return null;
    }
}