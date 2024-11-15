package htmleditor.command;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import htmleditor.TestData;
import htmleditor.model.HtmlDocument;

public class CommandManagerTest {

    @Test
    public void testExecute() {
        CommandManager manager = new CommandManager();
        HtmlDocument document = TestData.sample();
        manager.execute(new InsertTagCommand(document, "list", "li", "item4", "Item 4"));
        assertEquals(4, document.findByID("list").getChildren().size());
        assertEquals(3, document.findByID("list").indexOf(document.findByID("item4")));
        manager.undo();
        assertEquals(3, document.findByID("list").getChildren().size());
        assertNull(document.findByID("item4"));
        manager.redo();
        assertEquals(4, document.findByID("list").getChildren().size());
        assertEquals(3, document.findByID("list").indexOf(document.findByID("item4")));
    }
}
