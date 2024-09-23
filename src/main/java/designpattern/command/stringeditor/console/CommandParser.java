package designpattern.command.stringeditor.console;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import designpattern.command.stringeditor.command.AppendCommand;
import designpattern.command.stringeditor.command.Command;
import designpattern.command.stringeditor.command.DeleteCommand;
import designpattern.command.stringeditor.command.InsertCommand;

/**
 * 将命令行参数封装成命令:
 * 
 * 追加命令： a 'string'
 * 插入命令： i pos 'string'
 * 删除命令： d pos len
 * 显示当前编辑缓存的内容: l
 * 显示所有支持的命令: h //TODO
 * 
 * 其中，字符串需要用单引号包裹，只支持对"\"和“‘”的转义，转义字符为"\"。
 * 
 */
public class CommandParser {

    // 将被''包裹的字符串提取出来, 并将字符串中的转义字符还原
    private static String extractStringContent(String str) {

        return str
                .replaceAll("^'", "")
                .replaceAll("'$", "")
                .replace("\\\\", "\\")
                .replace("\\'", "'");
    }

    public static Command parse(String commandline) throws InvalidCommandException {

        String appendRegex = "^a\\s+'((?:[^'\\\\]|\\\\.)*?)'$";
        String insertRegex = "^i\\s+(\\d+)\\s+('((?:[^'\\\\]|\\\\.)*?)')$";
        String deleteRegex = "^d\\s+(\\d+)\\s+(\\d+)$";

        if (commandline.matches(appendRegex)) {
            Matcher m = Pattern.compile(appendRegex).matcher(commandline);
            if (m.matches()) {
                String value = m.group(1);
                return new AppendCommand(extractStringContent(value));
            } else
                throw new InvalidCommandException("Invalid append command: " + commandline);
        } else if (commandline.matches(insertRegex)) {
            Matcher m1 = Pattern.compile(insertRegex).matcher(commandline);
            if (m1.matches()) {
                String pos = m1.group(1);
                String value = m1.group(2);
                return new InsertCommand(extractStringContent(value), Integer.parseInt(pos));
            } else
                throw new InvalidCommandException("Invalid insert command: " + commandline);
        } else if (commandline.matches(deleteRegex)) {
            Matcher m2 = Pattern.compile(deleteRegex).matcher(commandline);
            if (m2.matches()) {
                int start = Integer.parseInt(m2.group(1));
                int end = start + Integer.parseInt(m2.group(2));
                return new DeleteCommand(start, end);
            } else
                throw new InvalidCommandException("Invalid delete command: " + commandline);
        } else if (commandline.equals("l")) {
            return new ShowStringCommand();
        }
        throw new InvalidCommandException("Invalid command: " + commandline);
    }
}