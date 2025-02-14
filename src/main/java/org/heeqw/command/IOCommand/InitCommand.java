package org.heeqw.command.IOCommand;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.heeqw.util.IdManager;

public class InitCommand extends IOCommand{
    public static final String TEMPLATE = """
            <html>
              <head>
                <title></title>
              </head>
              <body></body>
            </html>""";

    @Override
    public void execute(){
        try {
            IdManager.getInstance().clear();

            Document doc = Jsoup.parse(TEMPLATE);


            doc.selectFirst("html").attr("id", "html");
            doc.selectFirst("head").attr("id", "head");
            doc.selectFirst("title").attr("id", "title");
            doc.selectFirst("body").attr("id", "body");

            IdManager.getInstance().registerId("html");
            IdManager.getInstance().registerId("head");
            IdManager.getInstance().registerId("title");
            IdManager.getInstance().registerId("body");

            editor.setDocument(doc);

            System.out.println("Registered IDs: " + IdManager.getInstance().getAllRegisteredIds());

        }catch (Exception e){
            throw new RuntimeException("Failed to initialize editor: " + e.getMessage(), e);
        }
    }
}
