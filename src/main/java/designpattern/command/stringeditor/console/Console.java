package designpattern.command.stringeditor.console;


import designpattern.command.stringeditor.command.Command;
import designpattern.command.stringeditor.command.CommandInvoker;
import designpattern.command.stringeditor.model.StringBuf;

public class Console {

    public static void main(String[] args) {
        StringBuf stringBuf = new StringBuf("");
        CommandInvoker commandInvoker = new CommandInvoker();
        while (true) {
            System.out.print("Enter command: ");
            String commandStr = System.console().readLine();
            if (commandStr.equals("exit")) {
                break;
            }
            try {
                Command command = CommandParser.parse(stringBuf, commandInvoker, commandStr);
                commandInvoker.storeAndExecute(command);
            } catch (InvalidCommandException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
