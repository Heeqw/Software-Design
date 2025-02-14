package org.heeqw;


import org.heeqw.command.Command;
import org.heeqw.command.CommandFactory;
import org.heeqw.editor.HTMLEditor;
import org.heeqw.util.CommandParser;
import org.heeqw.util.ConsoleColors;
import org.heeqw.util.IdManager;

import java.util.Scanner;
import java.util.Optional;

public class Main {
    private static final HTMLEditor editor = HTMLEditor.getInstance();
    private static final CommandFactory commandFactory = new CommandFactory();
    private static final Scanner scanner = new Scanner(System.in);
    private static final String PROMPT = "html-editor> ";

    public static void main(String[] args) {
        printWelcomeMessage();

        while (true) {
            try {
                // 显示提示符
                System.out.print(ConsoleColors.BLUE + PROMPT + ConsoleColors.RESET);

                String input = scanner.nextLine().trim();

                // 处理特殊命令
                if (handleSpecialCommands(input)) {
                    continue;
                }

                // 处理正常命令
                processCommand(input);

            } catch (Exception e) {
                printError("Error: " + e.getMessage());
            }
        }
    }

    private static boolean handleSpecialCommands(String input) {
        if (input.isEmpty()) {
            return true;
        }

        switch (input.toLowerCase()) {
            case "exit", "quit" -> {
                System.out.println(ConsoleColors.YELLOW + "Exiting HTML Editor..." + ConsoleColors.RESET);
                scanner.close();
                System.exit(0);
                return true;
            }
            case "help" -> {
                printHelp();
                return true;
            }
            case "status" -> {
                printEditorStatus();
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    private static void processCommand(String input) {
        // 使用CommandParser解析命令和参数
        Optional<CommandParser.ParsedCommand> parsedCommand = CommandParser.parse(input);

        if (parsedCommand.isEmpty()) {
            printError("Invalid command format");
            System.out.println("Type 'help' for available commands.");
            return;
        }

        CommandParser.ParsedCommand cmd = parsedCommand.get();

        // 检查编辑器初始化状态
        if (!editor.isInitialized() && !isInitializationCommand(cmd.name())) {
            printWarning("Please initialize the editor using 'init' or 'read' command first.");
            return;
        }

        try {
            // 创建并执行命令
            Command command = CommandFactory.createCommand(cmd.name(), cmd.args());

            if (command == null) {
                printError("Unknown command: " + cmd.name());
                System.out.println("Type 'help' for available commands.");
                return;
            }

            // 执行命令并显示结果
            editor.executeCommand(command);
            printSuccess("Command executed successfully.");

        } catch (IllegalArgumentException e) {
            printError("Invalid command or arguments: " + e.getMessage());
            System.out.println("Type 'help' for available commands.");
        }
    }

    private static boolean isInitializationCommand(String commandName) {
        return commandName.equals("init") || commandName.equals("read");
    }

    private static void printEditorStatus() {
        System.out.println("\nEditor Status:");
        System.out.println("Initialized: " + editor.isInitialized());
        System.out.println("Can Undo: " + editor.canUndo());
        System.out.println("Can Redo: " + editor.canRedo());
        System.out.println("Current IDs: " + IdManager.getInstance().getAllRegisteredIds());
    }

    private static void printSuccess(String message) {
        System.out.println(ConsoleColors.GREEN + "Success: " + message + ConsoleColors.RESET);
    }

    private static void printError(String message) {
        System.err.println(ConsoleColors.RED + "Failure: " + message + ConsoleColors.RESET);
    }

    private static void printWarning(String message) {
        System.out.println(ConsoleColors.YELLOW + "Warning: " + message + ConsoleColors.RESET);
    }

    private static void printWelcomeMessage() {
        String welcome = """
            ╔════════════════════════════════════════╗
            ║          Welcome to HTML Editor        ║
            ╚════════════════════════════════════════╝
            """;
        System.out.println(ConsoleColors.CYAN + welcome + ConsoleColors.RESET);
        System.out.println("Type 'help' for available commands");
        System.out.println("Type 'exit' to quit");
        System.out.println(ConsoleColors.CYAN + "═".repeat(40) + ConsoleColors.RESET);
    }

    private static void printHelp() {
        String help = """
            Available Commands:
            
            Edit Commands:
              insert <tagName> <idValue> <insertLocation> [textContent]
              append <tagName> <idValue> <parentElement> [textContent]
              edit-id <oldId> <newId>
              edit-text <element> [newTextContent]
              delete <element>
            
            Display Commands:
              print-indent [indent]  - Print HTML with indentation
              print-tree            - Print HTML as tree structure
              spell-check          - Check spelling in text content
            
            File Commands:
              init                 - Initialize empty HTML template
              read <filepath>      - Read HTML from file
              save <filepath>      - Save HTML to file
            
            History Commands:
              undo                 - Undo last edit operation
              redo                 - Redo last undone operation
            
            Other Commands:
              status              - Show editor status
              help                - Show this help message
              exit                - Exit the editor
            
            Examples:
              init
              append div main body
              append p para1 main "This is a paragraph"
              print-tree
            """;

        System.out.println(ConsoleColors.CYAN + help + ConsoleColors.RESET);
    }
}
