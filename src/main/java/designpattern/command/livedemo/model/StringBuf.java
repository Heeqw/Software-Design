package designpattern.command.livedemo.model;

//实现字符串缓冲区，包括对字符串的追加、删除、插入等操作
public class StringBuf {

    private StringBuilder sb;

    public StringBuf(String buf) {
        sb = new StringBuilder(buf);
    }

    public StringBuf() {
        sb = new StringBuilder();
    }

    public String getString() {
        return sb.toString();
    }

    public void append(String str) {
        sb.append(str);
    }

    /*
     * 删除指定位置的字符串, 如果end 大于字符串的长度，则删除到字符串末尾
     * 如果start 大于字符串的长度，则什么也不做。
     */
    public void delete(int start, int end) {
        if (start > sb.length()) {
            return;
        }
        if (end > sb.length()) {
            end = sb.length();
        }
        sb.delete(start, end);
    }

    /**
     * 插入字符串到指定位置。如果index大于字符串的长度，则插入到字符串末尾，
     * 如果index小于0，则什么也不做
     */
    public void insert(int index, String str) {
        if (index > sb.length()) {
            index = sb.length();
        }
        if (index < 0) {
            return;
        }
        sb.insert(index, str);
    }

    // 获取子串
    public String substring(int start, int end) {
        return sb.substring(start, end);
    }

}
