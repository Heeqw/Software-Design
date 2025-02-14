package command;

import org.heeqw.command.DisplayCommand.PrintIndentCommand;
import org.heeqw.command.DisplayCommand.PrintTreeCommand;
import org.heeqw.command.EditCommand.AppendCommand;
import org.heeqw.command.IOCommand.InitCommand;
import org.heeqw.editor.HTMLEditor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DisplayCommandTest {
    private HTMLEditor editor;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        editor = HTMLEditor.getInstance();
        editor.executeCommand(new InitCommand());
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testPrintTree() {
        // 准备测试数据
        editor.executeCommand(new AppendCommand("div", "main", "body", "test content"));

        // 执行打印命令
        editor.executeCommand(new PrintTreeCommand());

        // 验证输出
        String output = outputStream.toString();
        assertTrue(output.contains("html"));
        assertTrue(output.contains("div#main"));
        assertTrue(output.contains("test content"));
    }

    @Test
    void testPrintIndent() {
        editor.executeCommand(new AppendCommand("div", "main", "body", "test content"));
        editor.executeCommand(new PrintIndentCommand(2));

        String output = outputStream.toString();
        assertTrue(output.contains("<div id=\"main\">"));
        assertTrue(output.contains("test content"));
    }
}
