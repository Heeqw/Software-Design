package htmleditor.command;

import htmleditor.model.HtmlDocument;

public class InsertTagCommand implements CanUndo {
    private HtmlDocument hd;
    private String parentId;
    private String tag;
    private String id;
    private String content;

    public InsertTagCommand(HtmlDocument hd, String parentId, String tag, String id, String content) {
        this.hd = hd;
        this.parentId = parentId;
        this.tag = tag;
        this.id = id;
        this.content = content;
    }

    @Override
    public void execute() {
        hd.addItem(parentId, tag, id, content);
    }

    @Override
    public void undo() {
        hd.deleteItem(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof InsertTagCommand) {
            InsertTagCommand other = (InsertTagCommand) obj;
            return this.parentId.equals(other.parentId) && this.tag.equals(other.tag) && this.id.equals(other.id)
                    && this.content.equals(other.content);
        }
        return false;
    }
}
