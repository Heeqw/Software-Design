package htmleditor.command;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import htmleditor.TestData;
import htmleditor.model.HtmlDocument;

public class DeleteByIdCommandTest {

    @Test
    public void testDeleteById() {
        HtmlDocument document = TestData.sample();
        DeleteByIdCommand command = new DeleteByIdCommand(document, "item1");
        command.execute();
        assertNull(document.findByID("item1"));
        assertEquals(2, document.findByID("list").getChildren().size());
    }

    @Test
    public void testUndo() {
        HtmlDocument document = TestData.sample();
        DeleteByIdCommand command = new DeleteByIdCommand(document, "item1");
        command.execute();
        assertNull(document.findByID("item1"));
        command.undo();
        assertEquals(3, document.findByID("list").getChildren().size());
        assertEquals("item1", document.findByID("item1").getId());
    }
}
