package htmleditor.io;

import org.junit.jupiter.api.Test;

import designpattern.adapter.tree.VisualTreeViewer;
import htmleditor.io.HtmlFileIO;
import htmleditor.io.treeview.HtmlTreeContentProvider;
import htmleditor.io.treeview.HtmlTreeLabelProvider;
import htmleditor.model.HtmlDocument;
import htmleditor.model.HtmlNode;

public class HtmlFileTest {
    @Test
    public void testRead() {
        String htmlContent = "<html>\n" + //
                "  <head>\n" + //
                "    <title>My Webpage</title>\n" + //
                "  </head>\n" + //
                "  <body>\n" + //
                "    <h1 id=\"title\">Welcome to my webpage</h1>\n" + //
                "    <p id=\"description\">This is a paragraph.</p>\n" + //
                "    <ul id=\"list\">\n" + //
                "      <li id=\"item1\">Item 1</li>\n" + //
                "      <li id=\"item2\">Item 2</li>\n" + //
                "      <li id=\"item3\">Item 3</li>\n" + //
                "    </ul>\n" + //
                "    <div id=\"footer\">\n" + //
                "      this is a text contect in div\n" + //
                "      <p id=\"last-updated\">Last updated: 2024-01-01</p>\n" + //
                "      <p id=\"copyright\">Copyright © 2021 MyWebpage.com</p>\n" + //
                "    </div>\n" + //
                "  </body>\n" + //
                "</html>";

        HtmlDocument hd = HtmlFileIO.read(htmlContent);
        VisualTreeViewer<HtmlNode> viewer = new VisualTreeViewer<>(new HtmlTreeContentProvider(hd.getRoot()),
                new HtmlTreeLabelProvider());

        viewer.show();

    }
}
