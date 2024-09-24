package designpattern.command.stringeditor.command;

public class UndoCommand implements Command {

    private CommandInvoker commandInvoker;

    public UndoCommand(CommandInvoker commandInvoker) {
        this.commandInvoker = commandInvoker;
    }

    @Override
    public void execute() {
        commandInvoker.undoLastCommand();
    }
}
