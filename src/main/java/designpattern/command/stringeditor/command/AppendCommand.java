package designpattern.command.stringeditor.command;

import designpattern.command.stringeditor.model.StringBuf;

public class AppendCommand implements CanUndoCommand {
    private final String text;
    private int previousLength;

    private StringBuf stringBuf;

    public AppendCommand(StringBuf stringBuf, String text) {
        this.text = text;
        this.stringBuf = stringBuf;
    }

    public static AppendCommand create(StringBuf stringBuf, String text) {
        return new AppendCommand(stringBuf, text);
    }

    @Override
    public void execute() {
        previousLength = stringBuf.length();
        stringBuf.append(text);
    }

    @Override
    public void undo() {
        stringBuf.delete(previousLength, stringBuf.length());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.text != null ? this.text.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AppendCommand other = (AppendCommand) obj;
        if ((this.text == null) ? (other.text != null) : !this.text.equals(other.text)) {
            return false;
        }
        return true;

    }

    @Override
    public String toString() {
        return "AppendCommand{" + "text=" + text + '}';
    }

}