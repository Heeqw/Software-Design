package org.heeqw.command.EditCommand;

import org.heeqw.model.HTMLElement;
import org.heeqw.util.IdManager;

public class EditIdCommand extends EditCommand{
    private final String oldId;
    private final String newId;

    public EditIdCommand(String oldId, String newId){
        this.oldId = oldId;
        this.newId = newId;
    }

    @Override
    public void execute() {
        validateParameters();
        performIdChange();
    }

    private void validateParameters() {
        // 验证原元素存在
        HTMLElement element = editor.getElementById(oldId);
        if (element == null) {
            System.out.println("Current registered IDs: " + IdManager.getInstance().getAllRegisteredIds());
            throw new IllegalArgumentException("Element not found: " + oldId);
        }

        // 验证是否是特殊标签
        if (element.isSpecialTag()) {
            throw new IllegalArgumentException("Cannot change ID of special tag: " + oldId);
        }

        // 验证新ID的唯一性
        if (!IdManager.getInstance().isIdAvailable(newId)) {
            System.out.println("Current registered IDs: " + IdManager.getInstance().getAllRegisteredIds());
            throw new IllegalArgumentException("New ID already exists: " + newId);
        }
    }

    private void performIdChange() {
        try {
            HTMLElement element = editor.getElementById(oldId);

            // 更新ID
            element.setId(newId);

            // 更新ID注册表
            IdManager.getInstance().removeId(oldId);
            IdManager.getInstance().registerId(newId);

        } catch (Exception e) {
            System.err.println("Error during ID change operation: " + e.getMessage());
            throw e;
        }
    }


}
