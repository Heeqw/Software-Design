package designpattern.command.livedemo.model;

// 支持字符串的追加、删除、插入等操作
public class StringBuf {

    private StringBuilder buffer;

    public StringBuf(String buf) {
        buffer = new StringBuilder(buf);
    }
    public void append(String str) {
        buffer.append(str);
    }

    // 删除指定位置的字符, 如果end大于字符串长度，则删除到字符串末尾，如果start大于end，则什么都不做
    public void delete(int start, int end) {
        if (end > buffer.length()) {
            end = buffer.length();
        }
        if (start > end) {
            return;
        }
        buffer.delete(start, end);
    }

    public void insert(int index, String str) {
        buffer.insert(index, str);
    }

    public String toString() {
        return buffer.toString();
    }

    public String getString() {
        return buffer.toString();
    }

}
