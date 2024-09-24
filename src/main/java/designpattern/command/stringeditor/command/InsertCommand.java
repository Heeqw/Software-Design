package designpattern.command.stringeditor.command;

import designpattern.command.stringeditor.model.StringBuf;

public class InsertCommand implements CanUndoCommand {
    private StringBuf stringBuf;
    private String str;
    private int index;

    public InsertCommand(StringBuf stringBuf, String str, int index) {
        this.str = str;
        this.index = index;
        this.stringBuf = stringBuf;
    }

    public static Command create(StringBuf stringBuf, String str, int index) {
        return new InsertCommand(stringBuf, str, index);
    }

    @Override
    public void execute() {
        stringBuf.insert(str, index);
    }

    @Override
    public void undo() {
        stringBuf.delete(index, index + str.length());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((str == null) ? 0 : str.hashCode());
        result = prime * result + index;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        System.out.println("equals");
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        InsertCommand other = (InsertCommand) obj;
        if (str == null) {
            if (other.str != null)
                return false;
        } else if (!str.equals(other.str))
            return false;
        if (index != other.index)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "InsertCommand [str=" + str + ", index=" + index + "]";

    }

}
