package designpattern.command.stringeditor.model;

public class StringBuf {

    private StringBuffer str;

    public StringBuf(String str) {
        this.str = new StringBuffer(str);
    }

    public void append(String str) {
        this.str.append(str);
    }

    // 删除指定位置范围内的字符并返回删除的字符串
    public String delete(int start, int end) {
        int _end = end > this.str.length() ? this.str.length() : end;
        String result = this.str.substring(start, _end);
        this.str.delete(start, _end).toString();
        return result;
    }

    public String getStr() {
        return str.toString();
    }

    public void insert(String str, int index) {
        this.str.insert(index, str);
    }

    public int length() {
        return this.str.length();
    }

    public String substring(int start, int end) {
        return this.str.substring(start, end);
    }

    @Override
    public String toString() {
        return str.toString();
    }
}