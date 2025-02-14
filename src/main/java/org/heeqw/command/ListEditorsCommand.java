package org.heeqw.command;

public class ListEditorsCommand extends Command{
   @Override
   public void execute() {
       WorkspaceManager.getInstance().listEditors();
   }

   @Override
   public CommandType getType() {
       return CommandType.DISPLAY;
   }
}
