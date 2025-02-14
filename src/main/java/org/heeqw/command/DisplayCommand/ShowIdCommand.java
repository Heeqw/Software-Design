package org.heeqw.command.DisplayCommand;

public class ShowIdCommand extends DisplayCommand{
    private final boolean showId;

    public ShowIdCommand(boolean showId) {
        this.showId = showId;
    }

    @Override
    public void execute() {
        if (!editor.isInitialized()) {
            throw new IllegalStateException("Editor not initialized");
        }
        editor.setShowId(showId);
    }
}
