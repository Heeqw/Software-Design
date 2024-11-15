package htmleditor.command;

import java.util.Stack;

public class CommandManager {
    private Stack<CanUndo> undoStack;
    private Stack<CanUndo> redoStack;

    public CommandManager() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public void execute(Command command) {
        command.execute();
        if (command instanceof CanUndo) {
            undoStack.push((CanUndo) command);
            redoStack.clear();
        }
    }

    public void undo() {
        if (undoStack.isEmpty())
            return;
        CanUndo command = undoStack.pop();
        command.undo();
        redoStack.push(command);
    }

    public void redo() {
        if (redoStack.isEmpty())
            return;
        CanUndo command = redoStack.pop();
        command.execute();
        undoStack.push(command);
    }
}
