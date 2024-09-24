package designpattern.command.stringeditor.command;

import designpattern.command.stringeditor.model.StringBuf;

public class DeleteCommand implements CanUndoCommand {

    private StringBuf stringBuf;
    private int start;
    private int end;
    private String deletedText;

    public DeleteCommand(StringBuf stringBuf, int start, int end) {
        this.start = start;
        this.end = end;
        this.stringBuf = stringBuf;
    }

    public static Command create(StringBuf stringBuf, int start, int end) {
        return new DeleteCommand(stringBuf, start, end);
    }

    public void execute() {
        deletedText = stringBuf.delete(start, end);
    }

    public void undo() {
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
