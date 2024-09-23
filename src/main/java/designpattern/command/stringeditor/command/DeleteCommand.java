package designpattern.command.stringeditor.command;

import designpattern.command.stringeditor.model.StringBuf;

public class DeleteCommand implements CanUndoCommand {

    private int start;
    private int end;
    private String deletedText;

    public DeleteCommand(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public static Command create(StringBuf stringBuf2, int start, int end) {
        return new DeleteCommand(start, end);
    }

    public void execute(StringBuf stringBuf) {
        deletedText = stringBuf.delete(start, end);
    }

    public void undo(StringBuf stringBuf) {
        stringBuf.insert(deletedText, start);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DeleteCommand other = (DeleteCommand) obj;
        if (this.start != other.start) {
            return false;
        }
        if (this.end != other.end) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.start;
        hash = 71 * hash + this.end;
        return hash;
    }

    @Override
    public String toString() {
        return "DeleteCommand{" + "start=" + start + ", end=" + end + '}';
    }

}
