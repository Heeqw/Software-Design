package org.heeqw;

import org.heeqw.editor.HTMLEditor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class WorkspaceManager {
   private final Map<String, HTMLEditor> editors = new HashMap<>();
   private String activeEditorId;
   private static WorkspaceManager instance;
   private final String configFilePath = ".my-html";

   private WorkspaceManager() {
       loadWorkspaceState();
   }

   public static WorkspaceManager getInstance() {
       if (instance == null) {
           instance = new WorkspaceManager();
       }
       return instance;
   }

   public HTMLEditor createEditor(String filepath) {
       if (editors.containsKey(filepath)) {
           throw new IllegalArgumentException("File already opened: " + filepath);
       }
       HTMLEditor editor = new HTMLEditor(filepath);
       editors.put(filepath, editor);
       activeEditorId = filepath;
       return editor;
   }

   public HTMLEditor getActiveEditor() {
       return editors.get(activeEditorId);
   }

   public void setActiveEditor(String fileId) {
       if (!editors.containsKey(fileId)) {
           throw new IllegalArgumentException("Editor not found: " + fileId);
       }
       activeEditorId = fileId;
   }

   public Collection<String> getOpenEditors() {
       return editors.keySet();
   }

   public void closeEditor(String fileId) {
       editors.remove(fileId);
       if (fileId.equals(activeEditorId)) {
           activeEditorId = editors.isEmpty() ? null : editors.keySet().iterator().next();
       }
   }

   public void saveWorkspaceState() {
       // 将编辑器列表、活动编辑器和showId设置保存到.my-html文件
   }

   private void loadWorkspaceState() {
       // 从.my-html文件加载上次的工作状态
   }
}
