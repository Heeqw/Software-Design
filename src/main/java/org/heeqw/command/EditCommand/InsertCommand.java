package org.heeqw.command.EditCommand;

import org.heeqw.model.HTMLElement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.heeqw.util.IdManager;



public class InsertCommand extends EditCommand {
    private final String tagName;
    private final String idValue;
    private final String insertLocationId;
    private final String textContent;


    public InsertCommand(String tagName, String idValue, String insertLocationId, String textContent){
        this.tagName = tagName;
        this.idValue = idValue;
        this.insertLocationId = insertLocationId;
        this.textContent = textContent;
    }

    @Override
    public void execute() {
        validateParameters();
        peformInsert();
    }

    private void validateParameters(){
        if (!IdManager.getInstance().isIdAvailable(idValue)) {
            System.out.println("Current registered IDs: " + IdManager.getInstance().getAllRegisteredIds());
            throw new IllegalArgumentException("ID already exists: " + idValue);
        }

        // 验证目标元素存在性
        HTMLElement target = editor.getElementById(insertLocationId);
        if (target == null) {
            System.out.println("Current registered IDs: " + IdManager.getInstance().getAllRegisteredIds());
            throw new IllegalArgumentException("Target element not found: " + insertLocationId);
        }
    }

    private void peformInsert(){
        try {
            // 创建新元素
            Document tempDoc = Jsoup.parse("<" + tagName + "></" + tagName + ">");
            HTMLElement target = editor.getElementById(insertLocationId);


            HTMLElement newElement = new HTMLElement(tempDoc.body().child(0));
            newElement.setId(idValue);

            // 设置文本内容
            if (textContent != null && !textContent.isEmpty()) {
                newElement.setText(textContent);
            }

            // 在目标元素之前插入

            target.insertBefore(newElement);

            // 注册ID
            IdManager.getInstance().registerId(idValue);

        } catch (Exception e) {
            System.err.println("Error during insert operation: " + e.getMessage());
            throw e;
        }
    }



}
