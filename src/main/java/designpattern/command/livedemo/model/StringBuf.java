package designpattern.command.livedemo.model;


/**
 * 字符串编辑器，支持字符串的追加、插入、删除等操作。
 */
public class StringBuf {

    private StringBuilder sb;

    public StringBuf(String str) {
        sb = new StringBuilder(str);
    }

    public void append(String str) {
        sb.append(str);
    }

    public void insert(int index, String str) {
        sb.insert(index, str);
    }

    // 删除指定范围的字符串，返回被删除的字符串, 如果end大于长度，则删除到末尾
    public String delete(int start, int end) {

        if (end > sb.length()) {
            end = sb.length();
        }
        String deleted = sb.substring(start, end);
        sb.delete(start, end);
        return deleted;
    }

    public String getStr() {
        return sb.toString();
    }

}