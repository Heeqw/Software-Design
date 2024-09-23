package designpattern.command.stringeditor.command;

import java.util.Stack;

import designpattern.command.stringeditor.model.StringBuf;

/**
 * CommandInvoker is responsible for executing and undoing commands.
 * It maintains a stack of commands and a stack of undone commands.
 * When a command is executed, it is added to the command stack.
 * When an undo is requested, the last command is popped from the command stack
 * and executed.
 */
public class CommandInvoker {
    private final Stack<CanUndoCommand> commandStack = new Stack<>();
    private final Stack<CanUndoCommand> undoneCommands = new Stack<>();

    private StringBuf stringBuf;

    public CommandInvoker(StringBuf stringBuf) {
        this.stringBuf = stringBuf;
    }
    public void storeAndExecute(Command command) {
        command.execute(stringBuf);
        if (command instanceof CanUndoCommand) {
            commandStack.push((CanUndoCommand) command);
            undoneCommands.clear();
        }
    }

    public void undoLastCommand() {
        if (!commandStack.isEmpty()) {
            CanUndoCommand command = commandStack.pop();
            command.undo(stringBuf);
            undoneCommands.push(command);
        }
    }

    public void redoLastCommand() {
        if (!undoneCommands.isEmpty()) {
            CanUndoCommand command = undoneCommands.pop();
            command.execute(stringBuf);
            commandStack.push(command);
        }
    }
}
