package com.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.command.Command;
import com.example.command.CommandFactory;
import com.example.config.Configuration;
import com.example.config.ConfigurationManager;
import com.example.core.Editor;
import com.example.core.SessionManager;
import com.example.exception.CommandException;
import com.example.exception.EditorException;

public class HtmlEditorApplication {
    private static final Logger logger = LoggerFactory.getLogger(HtmlEditorApplication.class);
    
    private final SessionManager sessionManager;
    private final CommandFactory commandFactory;
    private final Configuration config;
    private boolean running;
    private final Scanner scanner;

    public HtmlEditorApplication() {
        this.sessionManager = SessionManager.getInstance();
        this.config = ConfigurationManager.getInstance().getConfiguration();
        this.commandFactory = new CommandFactory();
        this.scanner = new Scanner(System.in);
        this.running = true;
    }

    public void start() {
        printWelcomeMessage();
        
        while (running) {
            try {
                System.out.print(getPrompt());
                String input = scanner.nextLine().trim();
                
                if (input.isEmpty()) {
                    continue;
                }
                
                processCommand(input);
                
            } catch (EditorException e) {
                System.err.println("Error: " + e.getMessage());
                logger.error("Command execution failed", e);
            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
                logger.error("Unexpected error occurred", e);
            }
        }
    }

    private void processCommand(String input) {
        List<String> args = parseCommandLine(input);
        if (args.isEmpty()) {
            return;
        }
        
        String commandName = args.remove(0).toLowerCase();
        processCommandWithArgs(commandName, args);
    }

    private List<String> parseCommandLine(String input) {
        List<String> args = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ' ' && !inQuotes) {
                if (current.length() > 0) {
                    args.add(current.toString());
                    current.setLength(0);
                }
            } else {
                current.append(c);
            }
        }
        
        if (current.length() > 0) {
            args.add(current.toString());
        }
        
        // 移除参数中的引号
        return args.stream()
            .map(arg -> arg.replaceAll("^\"|\"$", ""))
            .collect(Collectors.toList());
    }

    private void processCommandWithArgs(String commandName, List<String> args) {
        switch (commandName) {
            case "exit":
                handleExit();
                break;
            
            case "load":
                if (args.isEmpty()) {
                    throw new CommandException("load", "File path required");
                }
                handleLoad(args.get(0));
                break;
            
            case "save":
                handleSave();
                break;
            
            case "close":
                handleClose();
                break;
            
            case "editor-list":
                handleEditorList();
                break;
            
            case "edit":
                if (args.isEmpty()) {
                    throw new CommandException("edit", "File path required");
                }
                handleEdit(args.get(0));
                break;
            
            case "insert":
                validateArgsCount(args, 3, 4, "insert <tagName> <idValue> <insertLocation> [textContent]");
                Command insertCmd = commandFactory.createInsertCommand(
                    sessionManager.getActiveEditor(),
                    args.get(0),  // tagName
                    args.get(1),  // idValue
                    args.get(2),  // insertLocation
                    args.size() > 3 ? args.get(3) : null  // textContent
                );
                executeCommand(insertCmd);
                break;
            
            case "append":
                validateArgsCount(args, 3, 4, "append <tagName> <idValue> <parentElement> [textContent]");
                Command appendCmd = commandFactory.createAppendCommand(
                    sessionManager.getActiveEditor(),
                    args.get(0),  // tagName
                    args.get(1),  // idValue
                    args.get(2),  // parentElement
                    args.size() > 3 ? args.get(3) : null  // textContent
                );
                executeCommand(appendCmd);
                break;
            
            case "edit-id":
                validateArgsCount(args, 2, 2, "edit-id <oldId> <newId>");
                Command editIdCmd = commandFactory.createEditIdCommand(
                    sessionManager.getActiveEditor(),
                    args.get(0),  // oldId
                    args.get(1)   // newId
                );
                executeCommand(editIdCmd);
                break;
            
            case "edit-text":
                validateArgsCount(args, 1, 2, "edit-text <element> [newTextContent]");
                Command editTextCmd = commandFactory.createEditTextCommand(
                    sessionManager.getActiveEditor(),
                    args.get(0),  // element
                    args.size() > 1 ? args.get(1) : ""  // newTextContent
                );
                executeCommand(editTextCmd);
                break;
            
            case "delete":
                validateArgsCount(args, 1, 1, "delete <element>");
                Command deleteCmd = commandFactory.createDeleteCommand(
                    sessionManager.getActiveEditor(),
                    args.get(0)   // element
                );
                executeCommand(deleteCmd);
                break;
            
            case "print-indent":
                int indent = args.isEmpty() ? config.getInt("editor.indentSize", 2) : Integer.parseInt(args.get(0));
                Command printIndentCmd = commandFactory.createPrintIndentCommand(
                    sessionManager.getActiveEditor(),
                    indent
                );
                executeCommand(printIndentCmd);
                break;
            
            case "print-tree":
                Command printTreeCmd = commandFactory.createPrintTreeCommand(
                    sessionManager.getActiveEditor()
                );
                executeCommand(printTreeCmd);
                break;
            
            case "spell-check":
                Command spellCheckCmd = commandFactory.createSpellCheckCommand(
                    sessionManager.getActiveEditor()
                );
                executeCommand(spellCheckCmd);
                break;
            
            case "undo":
                handleUndo();
                break;
            
            case "redo":
                handleRedo();
                break;

            case "showid":
                if (args.isEmpty() || !isValidBoolean(args.get(0))) {
                    throw new CommandException("showid", "Boolean value (true/false) required");
                }
                handleShowId(Boolean.parseBoolean(args.get(0)));
                break;
                
            case "dir-tree":
                Command dirTreeCmd = commandFactory.createDirTreeCommand();
                executeCommand(dirTreeCmd);
                break;
                
            case "dir-indent":
                int dirIndent = args.isEmpty() ? config.getInt("editor.indentSize", 2) : Integer.parseInt(args.get(0));
                Command dirIndentCmd = commandFactory.createDirIndentCommand(dirIndent);
                executeCommand(dirIndentCmd);
                break;
            
            case "help":
                printHelp();
                break;
                
            default:
                throw new CommandException(commandName, "Unknown command");
        }
    }

    private void executeCommand(Command command) {
        Editor activeEditor = sessionManager.getActiveEditor();
        if (activeEditor == null && !isGlobalCommand(command)) {
            throw new EditorException("No active editor");
        }
        
        if (activeEditor != null) {
            activeEditor.executeCommand(command);
        } else {
            command.execute();
        }
    }

    private boolean isGlobalCommand(Command command) {
        return command.getClass().getSimpleName().startsWith("Dir") ||
               command.getClass().getSimpleName().equals("PrintEditorListCommand");
    }

    private void validateArgsCount(List<String> args, int minCount, int maxCount, String usage) {
        if (args.size() < minCount || args.size() > maxCount) {
            throw new CommandException("Invalid argument count", "Usage: " + usage);
        }
    }

    private void handleLoad(String filePath) {
        try {
            sessionManager.loadFile(filePath);
            System.out.println("Loaded file: " + filePath);
        } catch (Exception e) {
            throw new CommandException("load", "Failed to load file: " + e.getMessage());
        }
    }

    private void handleSave() {
        Editor activeEditor = getActiveEditorOrThrow();
        activeEditor.save();
        System.out.println("File saved: " + activeEditor.getFilePath());
    }

    private void handleClose() {
        Editor activeEditor = sessionManager.getActiveEditor();
        if (activeEditor == null) {
            return;
        }

        if (activeEditor.isModified()) {
            System.out.print("Save changes to " + activeEditor.getFilePath() + "? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();
            if (response.equals("y")) {
                Command saveCmd = commandFactory.createSaveCommand(activeEditor);
                executeCommand(saveCmd);
            } else {
                // 如果用户选择不保存，强制重置修改状态
                activeEditor.reloadDocument();
            }
        }
        
        Command closeCmd = commandFactory.createCloseCommand(activeEditor);
        executeCommand(closeCmd);
        
        // 显示更新后的编辑器列表
        handleEditorList();
    }

    private void handleEditorList() {
        List<String> openFiles = sessionManager.getOpenFiles();
        Editor activeEditor = sessionManager.getActiveEditor();
        
        if (openFiles.isEmpty()) {
            System.out.println("No open files");
            return;
        }
        
        for (String filepath : openFiles) {
            StringBuilder line = new StringBuilder();
            
            // 获取当前遍历的文件对应的编辑器
            Editor editor = sessionManager.getEditorByPath(filepath);
            
            // 通过比较编辑器实例来判断是否是活动编辑器
            if (editor != null && editor == activeEditor) {
                line.append("> ");
            } else {
                line.append("  ");  // 保持对齐
            }
            
            // 添加文件路径
            line.append(filepath);
            
            // 添加修改标记
            if (editor != null && editor.isModified()) {
                line.append(" *");
            }
            
            System.out.println(line);
        }
    }

    private void handleEdit(String filePath) {
        try {
            String fullPath = sessionManager.findFullPath(filePath);
            Editor editor = sessionManager.getEditorByPath(fullPath);
            if (editor == null) {
                throw new CommandException("edit", "File not open: " + filePath);
            }
            sessionManager.setActiveEditor(editor);
            System.out.println("Switched to: " + fullPath);
        } catch (IllegalArgumentException e) {
            throw new CommandException("edit", e.getMessage());
        }
    }

    private void handleUndo() {
        Editor activeEditor = getActiveEditorOrThrow();
        activeEditor.undo();
    }

    private void handleRedo() {
        Editor activeEditor = getActiveEditorOrThrow();
        activeEditor.redo();
    }

    private void handleShowId(boolean show) {
        Editor activeEditor = getActiveEditorOrThrow();
        activeEditor.setShowId(show);
    }

    private void handleExit() {
        List<Editor> modifiedEditors = sessionManager.getModifiedEditors();
        for (Editor editor : modifiedEditors) {
            System.out.printf("Save changes to %s? (y/n): ", editor.getFilePath());
            String response = scanner.nextLine().trim().toLowerCase();
            if (response.equals("y")) {
                editor.save();
            }
        }
        sessionManager.saveSession();
        running = false;
    }

    private Editor getActiveEditorOrThrow() {
        Editor activeEditor = sessionManager.getActiveEditor();
        if (activeEditor == null) {
            throw new EditorException("No active editor");
        }
        return activeEditor;
    }

    private boolean isValidBoolean(String value) {
        return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
    }

    private String getPrompt() {
        Editor activeEditor = sessionManager.getActiveEditor();
        return activeEditor != null ? 
            activeEditor.getFilePath() + "> " : 
            "> ";
    }

    private void printWelcomeMessage() {
        System.out.println("HTML Editor v1.0");
        System.out.println("Type 'help' for list of commands");
        System.out.println();
    }

    private void printHelp() {
        System.out.println("Available commands:");
        System.out.println("  load <filepath>              - Load HTML file");
        System.out.println("  save                         - Save current file");
        System.out.println("  close                        - Close current file");
        System.out.println("  editor-list                  - Show open files");
        System.out.println("  edit <filepath>              - Switch to file");
        System.out.println("  insert <tag> <id> <loc> [text] - Insert element");
        System.out.println("  append <tag> <id> <parent> [text] - Append element");
        System.out.println("  edit-id <old> <new>         - Change element ID");
        System.out.println("  edit-text <id> [text]       - Change element text");
        System.out.println("  delete <id>                 - Delete element");
        System.out.println("  print-tree                  - Show tree view");
        System.out.println("  print-indent [size]         - Show indented view");
        System.out.println("  spell-check                 - Check spelling");
        System.out.println("  showid true/false           - Toggle ID display");
        System.out.println("  dir-tree                    - Show directory tree");
        System.out.println("  dir-indent [size]           - Show directory indented");
        System.out.println("  undo                        - Undo last change");
        System.out.println("  redo                        - Redo last change");
        System.out.println("  exit                        - Exit editor");
    }
}
