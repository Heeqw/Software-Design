package htmleditor.command;

public class UndoCommand implements Command {
    private CommandManager commandManager;

    public UndoCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public void execute() {
        commandManager.undo();
    }
}
