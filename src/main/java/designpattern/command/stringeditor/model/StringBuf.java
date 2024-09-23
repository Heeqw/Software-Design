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


    // 在指定位置插入字符串, index为0或负数表示插入到开头, index为length或大于length表示插入到结尾
    public void insert(String str, int index) {
        if (index < 0) {
            index = 0;
        } else if (index > this.str.length()) {
            index = this.str.length();
        }
        this.str.insert(index, str);
    }

    public int length() {
        return this.str.length();
    }

    public String substring(int start, int end) {
        return this.str.substring(start, end);
    }

    public String getString() {
        return str.toString();
    }

    @Override
    public String toString() {
        return "StringBuf [str=" + str + "]";
    }

}