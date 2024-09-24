package designpattern.command.stringeditor.command;

public class RedoCommand implements Command {

    private CommandInvoker commandInvoker;

    public RedoCommand(CommandInvoker commandInvoker) {
        this.commandInvoker = commandInvoker;
    }

    @Override
    public void execute() {
        commandInvoker.redoLastCommand();
    }

}
