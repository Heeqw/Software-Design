package org.heeqw.command;

import org.heeqw.command.ControlCommand.RedoCommand;
import org.heeqw.command.ControlCommand.UndoCommand;
import org.heeqw.command.DisplayCommand.PrintIndentCommand;
import org.heeqw.command.DisplayCommand.PrintTreeCommand;
import org.heeqw.command.DisplayCommand.SpellCheckCommand;
import org.heeqw.command.EditCommand.*;
import org.heeqw.command.IOCommand.InitCommand;
import org.heeqw.command.IOCommand.ReadCommand;
import org.heeqw.command.IOCommand.SaveCommand;


public class CommandFactory {
    public static Command createCommand(String commandType, String... args){
        return switch (commandType.toLowerCase()){
            case "insert" -> new InsertCommand(
                    args[0],                            //tagName
                    args[1],                            //idValue
                    args[2],                            //insertLocation
                    args.length > 3 ? args[3] : null    //textContent
            );

            case "append" -> new AppendCommand(
                    args[0],
                    args[1],
                    args[2],
                    args.length > 3 ? args[3] : null
            );

            case "edit-id" -> new EditIdCommand(
                    args[0],                            //oldId
                    args[1]                             //newId
            );

            case "edit-text" -> new EditTextCommand(
                    args[0],
                    args.length > 1 ? args[1] : ""
            );

            case "delete" -> new DeleteCommand(
                    args[0]
            );

            case "print-indent" -> new PrintIndentCommand(
                    args.length > 0 ? Integer.parseInt(args[0]) : 2
            );

            case "print-tree" -> new PrintTreeCommand();

            case "spell-check" -> new SpellCheckCommand();

            case "read" -> new ReadCommand(args[0]);

            case "save" -> new SaveCommand(args[0]);

            case "init" -> new InitCommand();

            case "undo" -> new UndoCommand();

            case "redo" -> new RedoCommand();

            default -> throw new IllegalArgumentException("Unknown command: " + commandType);
        };
    }
}
