package htmleditor.model;

import org.junit.jupiter.api.Test;

import htmleditor.TestData;

import static org.junit.jupiter.api.Assertions.*;

public class HtmlDocumentTest {

    @Test
    public void testCreateChild() {
        HtmlDocument document = new HtmlDocument();
        document.addItem("root", "H1", "title", "Welcome to the HTML Editor");
        document.addItem("root", "P", "description", "This is a description of the HTML Editor");
        document.addItem("root", "UL", "list", "");
        document.addItem("list", "LI", "item1", "Item 1");
        document.addItem("list", "LI", "item2", "Item 2");

        assertEquals("title", document.findByID("title").getId());
        assertEquals("description", document.findByID("description").getId());
        assertEquals("list", document.findByID("list").getId());
        assertEquals("item1", document.findByID("item1").getId());
        assertEquals("item2", document.findByID("item2").getId());
    }

    @Test
    public void testDeleteItem() {
        HtmlDocument document = TestData.sample();
        document.deleteItem("item1");
        assertNull(document.findByID("item1"));
        assertNotNull(document.findByID("item2"));
        document.deleteItem("list");
        assertNull(document.findByID("list"));
        assertNull(document.findByID("item2"));
    }

    @Test
    public void testInsertChildAt() {
        HtmlDocument document = TestData.sample();
        HtmlNode node = document.findByID("list").insertChildAt(1, "li", "item1.1", "Item 1.1");
        assertEquals("item1.1", node.getId());
        assertEquals("Item 1.1", node.getContent());
        assertEquals("li", node.getTag());
        assertEquals(4, document.findByID("list").getChildren().size());
        assertEquals(1, document.findByID("list").indexOf(node));
    }
}
