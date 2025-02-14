package org.heeqw.command.EditCommand;

import org.heeqw.model.HTMLElement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.heeqw.util.IdManager;

public class AppendCommand extends EditCommand {
    private final String tagName;
    private final String idValue;
    private final String parentId;
    private final String textContent;

    public AppendCommand(String tagName, String idValue, String parentId, String textContent){
        this.tagName = tagName;
        this.idValue = idValue;
        this.parentId = parentId;
        this.textContent = textContent;
    }

    @Override
    public void execute(){
        validateParameters();
        performAppend();
    }

    private void validateParameters(){
        if (!IdManager.getInstance().isIdAvailable(idValue)){
            System.out.println("Current registered IDs: " + IdManager.getInstance().getAllRegisteredIds());
            throw new IllegalArgumentException("ID already exists: " + idValue);
        }
        HTMLElement parent = editor.getElementById(parentId);
        if (parent == null) {
            System.out.println("Current registered IDs: " + IdManager.getInstance().getAllRegisteredIds());
            throw new IllegalArgumentException("Parent element not found: " + parentId);
        }
    }

    private void performAppend() {
        try {
            // 创建新元素
            Document tempDoc = Jsoup.parse("<" + tagName + "></" + tagName + ">");

            HTMLElement parent = editor.getElementById(parentId);

//            HTMLElement newElement = new HTMLElement(tempDoc.body().child(0)，parent.getEditor());  //替换下面
            HTMLElement newElement = new HTMLElement(tempDoc.body().child(0));
            newElement.setId(idValue);

            // 设置文本内容
            if (textContent != null && !textContent.isEmpty()) {
                newElement.setText(textContent);
            }

            // 添加到父元素
            parent.appendChild(newElement);

            // 注册ID
            IdManager.getInstance().registerId(idValue);

        } catch (Exception e) {
            System.err.println("Error during append operation: " + e.getMessage());
            throw e;
        }
    }


}
