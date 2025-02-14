package command;

import org.heeqw.command.EditCommand.AppendCommand;
import org.heeqw.command.EditCommand.DeleteCommand;
import org.heeqw.command.EditCommand.EditIdCommand;
import org.heeqw.command.IOCommand.InitCommand;
import org.heeqw.editor.HTMLEditor;
import org.heeqw.util.IdManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EditCommandTest {
    private HTMLEditor editor;

    @BeforeEach
    void setUp() {
        editor = HTMLEditor.getInstance();
        IdManager.getInstance().clear();
        editor.executeCommand(new InitCommand());
    }

    @Test
    void testAppendCommand() {
        // 测试添加元素
        AppendCommand appendCmd = new AppendCommand("div", "main", "body", "test content");
        editor.executeCommand(appendCmd);

        assertNotNull(editor.getElementById("main"));
        assertEquals("test content", editor.getElementById("main").getDirectText());
    }

    @Test
    void testDeleteCommand() {
        // 先添加一个元素
        editor.executeCommand(new AppendCommand("div", "main", "body", null));

        // 测试删除
        DeleteCommand deleteCmd = new DeleteCommand("main");
        editor.executeCommand(deleteCmd);

        assertNull(editor.getElementById("main"));
        assertFalse(IdManager.getInstance().getAllRegisteredIds().contains("main"));
    }

    @Test
    void testEditIdCommand() {
        // 先添加一个元素
        editor.executeCommand(new AppendCommand("div", "main", "body", null));

        // 测试修改ID
        EditIdCommand editIdCmd = new EditIdCommand("main", "newId");
        editor.executeCommand(editIdCmd);

        assertNull(editor.getElementById("main"));
        assertNotNull(editor.getElementById("newId"));
    }
}
