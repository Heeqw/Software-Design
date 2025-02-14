package model;

import org.heeqw.model.HTMLElement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HTMLElementTest {
    private HTMLElement element;
    private Document doc;

    @BeforeEach
    void setup(){
        doc = Jsoup.parse("<div id='test'><p>Some text</p></div>");
        element = new HTMLElement(doc.selectFirst("div"));
    }

    @Test
    void testGetId(){
        assertEquals("test", element.getId());
    }

    @Test
    void testSetId(){
        element.setId("newId");
        assertEquals("newId", element.getId());
    }

    @Test
    void testGetDirectText(){
        HTMLElement elem = new HTMLElement(Jsoup.parse("<div>Direct text<span>Child text</span></div>").selectFirst("div"));
        assertEquals("Direct text", elem.getDirectText().trim());
    }

    @Test
    void testGetChildren() {
        assertEquals(1, element.getChildren().size());
        assertEquals("p", element.getChildren().get(0).getTagName());
    }

    @Test
    void testIsSpecialTag() {
        HTMLElement htmlElement = new HTMLElement(Jsoup.parse("<html></html>").selectFirst("html"));
        assertTrue(htmlElement.isSpecialTag());
        assertFalse(element.isSpecialTag());
    }
}
