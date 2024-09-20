package designpattern.command.stringeditor.command;

import java.util.Stack;

public class CommandInvoker {
    private final Stack<Command> commandStack = new Stack<>();
    private final Stack<Command> undoneCommands = new Stack<>();

    public void storeAndExecute(Command command) {
        command.execute();
        commandStack.push(command);
        undoneCommands.clear();
    }

    public void undoLastCommand() {
        if (!commandStack.isEmpty()) {
            Command command = commandStack.pop();
            command.undo();
            undoneCommands.push(command);
        }
    }

    public void redoLastCommand() {
        if (!undoneCommands.isEmpty()) {
            Command command = undoneCommands.pop();
            command.execute();
            commandStack.push(command);
        }
    }
}
