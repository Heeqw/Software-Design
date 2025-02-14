package com.example.command.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.command.Command;
import com.example.core.Editor;
import com.example.core.SessionManager;
import com.example.exception.CommandException;

public class CloseCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(CloseCommand.class);

    private final SessionManager sessionManager;
    private final Editor editor;
    private boolean wasActive;
    private boolean wasModified;
    private String content;

    public CloseCommand(SessionManager sessionManager, Editor editor) {
        this.sessionManager = sessionManager;
        this.editor = editor;
    }

    @Override
    public void execute() {
        try {
            // 保存当前状态用于撤销
            wasActive = (editor == sessionManager.getActiveEditor());
            wasModified = editor.isModified();
            
            // 如果编辑器被修改，先保存内容并重置状态
            if (wasModified) {
                content = editor.getDocument().html();
                // 重要：在关闭之前重置修改状态
                editor.resetModifiedState();
            }

            // 再次检查修改状态是否已重置
            if (editor.isModified()) {
                throw new CommandException(getCommandName(), 
                    "Editor is still marked as modified after reset");
            }

            // 关闭编辑器
            boolean closed = sessionManager.closeEditor(editor);
            if (!closed) {
                throw new CommandException(getCommandName(), 
                    "Failed to close editor: " + editor.getFilePath());
            }

            logger.info("Successfully closed file: {}", editor.getFilePath());
        } catch (Exception e) {
            logger.error("Failed to close editor: {}", editor.getFilePath(), e);
            throw new CommandException(getCommandName(), 
                "Failed to close editor: " + e.getMessage());
        }
    }

    @Override
    public void undo() {
        try {
            // 重新加载文件
            Editor reopenedEditor = sessionManager.loadFile(editor.getFilePath());
            
            // 恢复修改状态和内容
            if (wasModified) {
                reopenedEditor.getDocument().html(content);
                reopenedEditor.markModified();
            }

            // 恢复活动状态
            if (wasActive) {
                sessionManager.setActiveEditor(reopenedEditor);
            }

            logger.info("Successfully reopened file: {}", editor.getFilePath());
        } catch (Exception e) {
            logger.error("Failed to reopen file: {}", editor.getFilePath(), e);
            throw new CommandException(getCommandName(), 
                "Failed to undo close command: " + e.getMessage());
        }
    }

    @Override
    public boolean isReversible() {
        return true;
    }
}