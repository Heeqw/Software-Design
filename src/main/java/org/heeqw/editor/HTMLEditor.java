package org.heeqw.editor;

import org.heeqw.command.*;

import org.heeqw.command.ControlCommand.UndoCommand;
import org.heeqw.command.ControlCommand.RedoCommand;
import org.heeqw.model.HTMLElement;
import org.heeqw.util.IdManager;
import org.jsoup.nodes.Document;
import java.util.HashSet;

import java.util.Stack;

public class HTMLEditor {

    private final String filepath;
    private HTMLElement rootElement;
    private final Stack<DocumentState> undoStack = new Stack<>();
    private final Stack<DocumentState> redoStack = new Stack<>();
    private boolean showId = true;
    private boolean isDirty = false; // 标记文件是否被修改

    public HTMLEditor(String filepath) {
        this.filepath = filepath;
    }

    // 获取文件路径
    public String getFilepath() {
        return filepath;
    }

    // 检查文件是否被修改
    public boolean isDirty() {
        return isDirty;
    }

    // 保存文件
    public void save() {
        // 保存文件内容
        isDirty = false;
    }

    public void executeCommand(Command command) {
        if (command == null) return;

        if (command instanceof UndoCommand) {
            performUndo();
            return;
        }
        if (command instanceof RedoCommand) {
            performRedo();
            return;
        }

        try {
            if (command.getType() == CommandType.EDIT) {
                undoStack.push(createCurrentState());
            }

            command.execute();
            // 命令执行成功后的处理
            switch (command.getType()) {
                case EDIT -> {
                    // 只有新的编辑命令成功执行后才清空重做栈
                    redoStack.clear();
                }
                case IO -> {
                    // IO命令清空所有历史
                    undoStack.clear();
                    redoStack.clear();
                }
            }
        } catch (Exception e) {
            if (command.getType() == CommandType.EDIT && !undoStack.isEmpty()) {
                restoreState(undoStack.pop());
            }
            throw e;
        }
    }

    private void performUndo() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }

        try {
            // 保存当前状态到重做栈
            DocumentState currentState = createCurrentState();
            redoStack.push(currentState);

            // 弹出并恢复到上一个状态
            DocumentState prevState = undoStack.pop();
            restoreState(prevState);

        } catch (Exception e) {
            System.err.println("Undo operation failed: " + e.getMessage());
            throw e;
        }
    }

    private void performRedo() {
        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo.");
            return;
        }

        try {
            // 保存当前状态到撤销栈
            DocumentState currentState = createCurrentState();
            undoStack.push(currentState);

            // 弹出并恢复到重做状态
            DocumentState redoState = redoStack.pop();
            restoreState(redoState);

        } catch (Exception e) {
            System.err.println("Redo operation failed: " + e.getMessage());
            throw e;
        }
    }

    private DocumentState createCurrentState() {
        if (rootElement == null) {
            return null;
        }
        return new DocumentState(
                rootElement.getJsoupElement().ownerDocument().clone(),
                new HashSet<>(IdManager.getInstance().getAllRegisteredIds())
        );
    }


    public Document getJsoupDocument() {
        return rootElement != null ? rootElement.getJsoupElement().ownerDocument() : null;
    }


    // 恢复到指定状态
    private void restoreState(DocumentState state) {
        if (state != null) {
            // 恢复文档
            setDocument(state.getDocument());

            // 恢复ID注册表
            IdManager.getInstance().clear();
            state.getRegisteredIds().forEach(id ->
                    IdManager.getInstance().registerId(id)
            );
        }
    }



    public HTMLElement getRootElement(){
        return rootElement;
    }

    public void setDocument(Document document) {
        if (document != null) {
//            rootElement = new HTMLElement(document.root(), this);
            rootElement = new HTMLElement(document);
            rootElement.initializeSpecialTags();
        } else {
            rootElement = null;
        }
    }


    public HTMLElement getElementById(String id) {
        if (rootElement != null) {
            return rootElement.getElementById(id);
        }
        return null;
    }


    public boolean isInitialized(){
        return rootElement != null;
    }




    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}

