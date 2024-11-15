package htmleditor.command;

public class RedoCommand implements Command {
    private CommandManager commandManager;

    public RedoCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public void execute() {
        commandManager.redo();
    }
}
