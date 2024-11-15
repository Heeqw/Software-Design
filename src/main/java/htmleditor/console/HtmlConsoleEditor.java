package htmleditor.console;

import java.util.Scanner;

import htmleditor.command.Command;
import htmleditor.command.CommandManager;
import htmleditor.model.HtmlDocument;
import htmleditor.service.SpellChecker;
import htmleditor.service.mock.SpellCheckImpl;

public class HtmlConsoleEditor {
    private CommandManager commandManager;
    private HtmlDocument document;

    private CommandFactory commandFactory;

    public HtmlConsoleEditor(HtmlDocument document, SpellChecker spellChecker) {
        this.commandManager = new CommandManager();
        this.document = document;
        this.commandFactory = new CommandFactory(spellChecker);
    }

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                Command command = commandFactory.createCommand(commandManager, document, input);
                if (command != null) {
                    commandManager.execute(command);
                }
            }
        }
    }

    public static void main(String[] args) {
        HtmlDocument document = TestData.sample();
        HtmlConsoleEditor editor = new HtmlConsoleEditor(document, new SpellCheckImpl());
        editor.run();
    }

}
