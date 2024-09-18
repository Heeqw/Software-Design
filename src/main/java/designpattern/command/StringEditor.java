package designpattern.command;

import java.util.Stack;

class StringBuffer {

    private String str;

    public StringBuffer(String str) {
        this.str = str;
    }

    public void append(String str) {
        this.str += str;
    }

    public void insert(String str, int index) {
        this.str = this.str.substring(0, index) + str + this.str.substring(index);
    }

    /**
     * 从字符串中删除指定范围的字符，并返回被删除的子字符串
     * 
     * @param start 删除范围的起始索引（包含）
     * @param end   删除范围的结束索引（不包含）
     * @return 返回被删除的子字符串
     * @throws StringIndexOutOfBoundsException 如果起始或结束索引超出当前字符串范围，将抛出此异常
     */
    public String delete(int start, int end) {
        String s = this.str.substring(start, end);
        this.str = this.str.substring(0, start) + this.str.substring(end);
        return s;
    }

    // length of string buffer
    public int length() {
        return this.str.length();
    }

    // substring
    public String substring(int start, int end) {
        return this.str.substring(start, end);
    }

    public String getStr() {
        return str;
    }
}

interface StringEditorCommand {

    void execute(StringBuffer buffer);

    void undo(StringBuffer buffer);

}

class AppendCommand implements StringEditorCommand {

    private String str;

    public AppendCommand(String str) {
        this.str = str;
    }

    @Override
    public void execute(StringBuffer buffer) {
        buffer.append(str);
    }

    @Override
    public void undo(StringBuffer buffer) {
        buffer.delete(buffer.length() - str.length(), buffer.length());
    }

}

class DeleteCommand implements StringEditorCommand {

    private int start;
    private int end;
    private String deleted;

    public DeleteCommand(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void execute(StringBuffer buffer) {
        deleted = buffer.delete(start, end);
    }

    @Override
    public void undo(StringBuffer buffer) {
        buffer.insert(deleted, start);
    }
}

class CommandExecutor {

    private StringBuffer buffer;

    private Stack<StringEditorCommand> commands = new Stack<>();
    private Stack<StringEditorCommand> undoCommands = new Stack<>();

    public CommandExecutor(StringBuffer buffer) {
        this.buffer = buffer;
    }

    public void execute(StringEditorCommand command) {
        undoCommands.clear();
        command.execute(buffer);
        commands.push(command);
    }

    public void undo() {
        StringEditorCommand topCommand = commands.pop();
        topCommand.undo(buffer);
        undoCommands.push(topCommand);
    }

    public void redo() {
        StringEditorCommand topCommand = undoCommands.pop();
        topCommand.execute(buffer);
        commands.push(topCommand);
    }

}

public class StringEditor {

    public static void main(String[] args) {
        StringBuffer buffer = new StringBuffer("Hello");
        CommandExecutor executor = new CommandExecutor(buffer);
        executor.execute(new AppendCommand(" World"));
        System.out.println(buffer.getStr());
        executor.undo();
        System.out.println(buffer.getStr());
        executor.redo();
        System.out.println(buffer.getStr());
        executor.undo();
        System.out.println(buffer.getStr());
        executor.execute(new AppendCommand(" New World"));
        System.out.println(buffer.getStr());

    }
}
