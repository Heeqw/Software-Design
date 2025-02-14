package org.heeqw.command;

public class SwitchEditorCommand extends Command{
   private final String targetEditorId;

   public SwitchEditorCommand(String targetEditorId) {
       this.targetEditorId = targetEditorId;
   }

   @Override
   public void execute() {
       WorkspaceManager.getInstance().setActiveEditor(targetEditorId);
   }

   @Override
   public CommandType getType() {
       return CommandType.CONTROL;
   }
}
