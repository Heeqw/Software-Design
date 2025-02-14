package org.heeqw.command.EditCommand;

import org.heeqw.model.HTMLElement;
import org.heeqw.util.IdManager;

public class DeleteCommand extends EditCommand{
    private final String elementId;


    public DeleteCommand(String elementId){
        this.elementId = elementId;
    }

    @Override
    public void execute(){
        validateParameters();
        performDelete();
    }

    private void validateParameters() {
        HTMLElement target = editor.getElementById(elementId);
        if (target == null) {
            System.out.println("Current registered IDs: " + IdManager.getInstance().getAllRegisteredIds());
            throw new IllegalArgumentException("Element not found: " + elementId);
        }

        // 检查是否是特殊标签
        if (target.isSpecialTag()) {
            throw new IllegalArgumentException("Cannot delete special tag: " + elementId);
        }
    }

    private void performDelete() {
        try {
            // 删除元素
            HTMLElement target = editor.getElementById(elementId);
            target.remove();

            // 取消ID注册
            IdManager.getInstance().removeId(elementId);

        } catch (Exception e) {
            System.err.println("Error during delete operation: " + e.getMessage());
            throw e;
        }
    }
}
