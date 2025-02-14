package editor;


import org.heeqw.command.ControlCommand.RedoCommand;
import org.heeqw.command.IOCommand.InitCommand;
import org.heeqw.editor.HTMLEditor;
import org.heeqw.command.ControlCommand.UndoCommand;
import org.heeqw.command.EditCommand.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HTMLEditorTest {
    private HTMLEditor editor;

    @BeforeEach
    void setUp() {
        editor = HTMLEditor.getInstance();
        editor.executeCommand(new InitCommand());
    }

    @Test
    void testUndoRedo() {
        // 执行一个编辑操作
        editor.executeCommand(new AppendCommand("div", "main", "body", "test"));
        assertNotNull(editor.getElementById("main"));

        // 测试撤销
        editor.executeCommand(new UndoCommand());
        assertNull(editor.getElementById("main"));

        // 测试重做
        editor.executeCommand(new RedoCommand());
        assertNotNull(editor.getElementById("main"));
    }

    @Test
    void testMultipleUndoRedo() {
        // 执行多个编辑操作
        editor.executeCommand(new AppendCommand("div", "main", "body", null));
        editor.executeCommand(new AppendCommand("p", "para1", "main", "text1"));
        editor.executeCommand(new AppendCommand("p", "para2", "main", "text2"));

        // 测试多次撤销
        editor.executeCommand(new UndoCommand()); // 撤销para2
        assertNull(editor.getElementById("para2"));
        assertNotNull(editor.getElementById("para1"));

        editor.executeCommand(new UndoCommand()); // 撤销para1
        assertNull(editor.getElementById("para1"));
        assertNotNull(editor.getElementById("main"));

        // 测试多次重做
        editor.executeCommand(new RedoCommand()); // 重做para1
        assertNotNull(editor.getElementById("para1"));
        assertNull(editor.getElementById("para2"));

        editor.executeCommand(new RedoCommand()); // 重做para2
        assertNotNull(editor.getElementById("para2"));
    }
}
