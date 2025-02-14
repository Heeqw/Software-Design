package command;

import org.heeqw.editor.HTMLEditor;
import org.heeqw.command.IOCommand.*;
import org.heeqw.command.EditCommand.AppendCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;


public class IOCommandTest {
    private HTMLEditor editor;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        editor = HTMLEditor.getInstance();
    }

    @Test
    void testSaveAndRead() {
        // 初始化并添加一些内容
        editor.executeCommand(new InitCommand());
        editor.executeCommand(new AppendCommand("div", "main", "body", "test content"));

        // 保存文件
        Path filePath = tempDir.resolve("test.html");
        editor.executeCommand(new SaveCommand(filePath.toString()));

        // 清除当前内容
        editor.executeCommand(new InitCommand());

        // 读取文件
        editor.executeCommand(new ReadCommand(filePath.toString()));

        // 验证内容是否正确恢复
        assertNotNull(editor.getElementById("main"));
        assertEquals("test content", editor.getElementById("main").getDirectText());
    }
}
