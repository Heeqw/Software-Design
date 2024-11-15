package htmleditor.command;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import htmleditor.TestData;
import htmleditor.model.HtmlDocument;

public class InsertTagCommandTest {

    @Test
    public void testInsertTag() {
        HtmlDocument document = TestData.sample();
        InsertTagCommand command = new InsertTagCommand(document, "list", "li", "item4", "Item 4");
        command.execute();
        assertEquals(4, document.findByID("list").getChildren().size());
        assertEquals(3, document.findByID("list").indexOf(document.findByID("item4")));
    }

    @Test
    public void testUndo() {
        HtmlDocument document = TestData.sample();
        InsertTagCommand command = new InsertTagCommand(document, "list", "li", "item4", "Item 4");
        command.execute();
        command.undo();
        assertEquals(3, document.findByID("list").getChildren().size());
        assertNull(document.findByID("item4"));
    }
}
