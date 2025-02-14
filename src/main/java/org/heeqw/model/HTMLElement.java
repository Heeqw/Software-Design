package org.heeqw.model;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

import java.util.*;

public class HTMLElement {
    private final Element jsoupElement;

//    private final HTMLEditor editor;  // 恢复为 final
//    private boolean hasSpellingError = false;

    public HTMLElement(Element element){
        this.jsoupElement = element;
    }


//    public HTMLElement(Element element, HTMLEditor editor) {
//        this.jsoupElement = element;
//        this.editor = editor;
//    }
//
//    // 用于根元素的构造函数
//    public HTMLElement(Document document, HTMLEditor editor) {
//        this(document.root(), editor);
//        initializeChildren();
//    }
//
//
//    // 工厂方法：创建子元素
//    protected HTMLElement createChild(Element element) {
//        return new HTMLElement(element, this.editor);
//    }
// 初始化子元素时使用工厂方法
//    private void initializeChildren() {
//        jsoupElement.children().forEach(child -> {
//            HTMLElement childElement = createChild(child);
//            childElement.initializeChildren();  // 递归初始化
//        });
//    }
//    public HTMLEditor getEditor() {
//        return editor;
//    }



    /**
     * 获取元素的标签名
     */
    public String getTagName() {
        return jsoupElement.tagName();
    }

    /**
     * 获取元素的ID
     */
    public String getId() {
        String id = jsoupElement.id();
        return id != null ? id : "";
    }



    /**
     * 使用给定标签创建新的HTMLElement
     */
    public static HTMLElement createElement(String tagName) {
        Document doc = Jsoup.parse("<" + tagName + "></" + tagName + ">");
        return new HTMLElement(doc.body().child(0));
    }

    /**
     * 初始化特殊标签的ID
     */
    public void initializeSpecialTags() {
        // 初始化html标签
        Element htmlElement = jsoupElement.selectFirst("html");
        if (htmlElement != null && !htmlElement.hasAttr("id")) {
            htmlElement.attr("id", "html");
        }

        // 初始化head标签
        Element headElement = jsoupElement.selectFirst("head");
        if (headElement != null && !headElement.hasAttr("id")) {
            headElement.attr("id", "head");
        }

        // 初始化title标签
        Element titleElement = jsoupElement.selectFirst("title");
        if (titleElement != null && !titleElement.hasAttr("id")) {
            titleElement.attr("id", "title");
        }

        // 初始化body标签
        Element bodyElement = jsoupElement.selectFirst("body");
        if (bodyElement != null && !bodyElement.hasAttr("id")) {
            bodyElement.attr("id", "body");
        }
    }

    /**
     * 设置元素的ID
     */
    public void setId(String id) {
        jsoupElement.attr("id", id);
    }

    /**
     * 获取元素的直接文本内容（不包括子元素的文本）
     */
    public String getDirectText() {
        StringBuilder text = new StringBuilder();
        jsoupElement.childNodes().stream()
                .filter(node -> node instanceof TextNode)
                .map(node -> ((TextNode) node).text().trim())
                .filter(str -> !str.isEmpty())
                .forEach(text::append);
        return text.toString();
    }

    /**
     * 设置元素的文本内容
     */
    public void setText(String text) {
        // 保存现有的子元素
        List<Element> childElements = new ArrayList<>(jsoupElement.children());

        // 清除所有内容
        jsoupElement.empty();

        // 如果有文本，添加为第一个节点
        if (text != null && !text.trim().isEmpty()) {
            jsoupElement.prependText(text);
        }

        // 重新添加子元素
        childElements.forEach(jsoupElement::appendChild);
    }

    /**
     * 获取父元素
     */
    public HTMLElement getParent() {
        Element parent = jsoupElement.parent();
        return parent != null ? new HTMLElement(parent) : null;
    }

    /**
     * 获取所有子元素
     */
    public List<HTMLElement> getChildren() {
        return jsoupElement.children().stream()
                .map(HTMLElement::new)
                .toList();
    }

    /**
     * 获取所有注册的ID
     */
    public Set<String> getAllIds() {
        Set<String> ids = new HashSet<>();
        for (Element element : jsoupElement.select("[id]")) {
            ids.add(element.attr("id"));
        }
        return ids;
    }

    /**
     * 添加子元素
     */
    public void appendChild(HTMLElement child) {
        jsoupElement.appendChild(child.getJsoupElement());
    }



    /**
     * 在当前元素之前插入新元素
     */
    public void insertBefore(HTMLElement newElement) {
        jsoupElement.before(newElement.getJsoupElement());
    }



    /**
     * 移除当前元素
     */
    public void remove() {
        jsoupElement.remove();
    }

    /**
     * 获取内部的jsoup Element对象（谨慎使用）
     */
    public Element getJsoupElement() {
        return jsoupElement;
    }

    /**
     * 创建元素的深拷贝
     */
    public HTMLElement clone() {
        return new HTMLElement(jsoupElement.clone());
    }
//    public HTMLElement clone() {
//        return new HTMLElement(jsoupElement.clone(), this.editor);
//    }

    /**
     * 判断是否是特殊标签（html, head, title, body）
     */
    public boolean isSpecialTag() {
        return getTagName().matches("^(html|head|title|body)$");
    }

    /**
     * 获取元素的所有文本内容（包括子元素的文本）
     */
    public String getAllText() {
        return jsoupElement.text();
    }

    /**
     * 根据ID查找子元素（包括深层子元素）
     */
    public HTMLElement getElementById(String id) {
        if (getId().equals(id)) {
            return this;
        }

        for (HTMLElement child : getChildren()) {
            HTMLElement result = child.getElementById(id);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HTMLElement that = (HTMLElement) o;
        return Objects.equals(jsoupElement, that.jsoupElement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jsoupElement);
    }

    @Override
    public String toString() {
        return jsoupElement.toString();
    }

//    public String getDisplayName() {
//        StringBuilder name = new StringBuilder();
//        if (hasSpellingError) {
//            name.append("[X]");
//        }
//        name.append(getTagName());
//        if (editor.isShowId() && !isSpecialTag()) {
//            name.append("#").append(getId());
//        }
//        return name.toString();
//    }
}
