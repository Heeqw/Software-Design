package htmleditor.command;

import htmleditor.model.HtmlDocument;
import htmleditor.model.HtmlNode;

public class DeleteByIdCommand implements CanUndo {
    private HtmlDocument hd;
    private String id;
    private int index;
    private HtmlNode deletedNode;

    public DeleteByIdCommand(HtmlDocument hd, String id) {
        this.hd = hd;
        this.id = id;
    }

    @Override
    public void execute() {
        HtmlNode node = hd.findByID(id);
        index = node.getParent().indexOf(node);
        deletedNode = hd.deleteItem(id);
    }

    @Override
    public void undo() {
        deletedNode.getParent().insertChildAt(index, deletedNode.getTag(), deletedNode.getId(),
                deletedNode.getContent());
    }
}
