package htmleditor.io;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import htmleditor.model.HtmlDocument;
import htmleditor.model.HtmlNode;

public class HtmlFileIO {

    public static HtmlDocument read(String htmlContent) {
        HtmlDocument hd = new HtmlDocument();
        // 使用Jsoup解析HTML字符串
        Document doc = Jsoup.parse(htmlContent);
        // 从Document的body开始构建HtmlNode树
        buildHtmlNode(doc.body(), hd.getRoot());
        return hd;
    }

    private static HtmlNode buildHtmlNode(Element element, HtmlNode parent) {
        // 创建当前节点
        HtmlNode node = parent.createChild(element.tagName(), element.id(), element.ownText());

        // 递归处理所有子节点
        for (Element child : element.children()) {
            buildHtmlNode(child, node);
        }

        return node;
    }
}
