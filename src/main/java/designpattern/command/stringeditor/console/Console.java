package designpattern.command.stringeditor.console;

import com.jnape.palatable.lambda.adt.Either;

import designpattern.command.stringeditor.command.Command;
import designpattern.command.stringeditor.command.CommandInvoker;
import designpattern.command.stringeditor.model.StringBuf;

public class Console {

    public static void main(String[] args) {
        StringBuf stringBuf = new StringBuf("");
        CommandInvoker commandInvoker = new CommandInvoker(stringBuf);
        while (true) {
            System.out.print("Enter command: ");
            String commandStr = System.console().readLine();
            if (commandStr.equals("exit")) {
                break;
            }
            try {
                Command command = CommandParser.parse(commandStr);
                commandInvoker.storeAndExecute(command);
            } catch (InvalidCommandException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
