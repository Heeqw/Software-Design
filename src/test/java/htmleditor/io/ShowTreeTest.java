package htmleditor.io;

import org.junit.jupiter.api.Test;

import designpattern.adapter.tree.VisualTreeViewer;
import htmleditor.TestData;
import htmleditor.io.treeview.HtmlTreeContentProvider;
import htmleditor.io.treeview.HtmlTreeLabelProvider;
import htmleditor.model.HtmlDocument;
import htmleditor.model.HtmlNode;

public class ShowTreeTest {
    @Test
    public void testShowTree() {
        HtmlDocument document = new HtmlDocument();
        document.addItem("root", "H1", "title", "Welcome to the HTML Editor");
        document.addItem("root", "P", "description", "This is a description of the HTML Editor");
        document.addItem("root", "UL", "list", "");
        document.addItem("list", "LI", "item1", "Item 1");
        document.addItem("list", "LI", "item2", "Item 2");

        VisualTreeViewer<HtmlNode> viewer = new VisualTreeViewer<>(new HtmlTreeContentProvider(document.getRoot()),
                new HtmlTreeLabelProvider());
        viewer.show();
    }

    public static void main(String[] args) {
        HtmlDocument document = TestData.sample();
        VisualTreeViewer<HtmlNode> viewer = new VisualTreeViewer<>(new HtmlTreeContentProvider(document.getRoot()),
                new HtmlTreeLabelProvider());
        viewer.show();
    }
}
