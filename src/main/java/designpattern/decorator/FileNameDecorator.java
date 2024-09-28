package designpattern.decorator;

import java.io.File;

import designpattern.adapter.tree.NameProvider;
import designpattern.adapter.tree.VisualTreeViewer;
import designpattern.adapter.tree.demo.DirTreeProvider;
import designpattern.adapter.tree.demo.FileNameProvider;

class StarDecorator implements NameProvider<File> {
    private NameProvider<File> decorated;

    public StarDecorator(NameProvider<File> decorated) {
        this.decorated = decorated;
    }

    @Override
    public String getName(File node) {
        String txt = decorated.getName(node);
        if (txt.startsWith("R"))
            return "*" + txt;
        else
            return txt;
    }

}

/**
 * FileNameDecorator 装饰器用于给 FileNameProvider 提供的目录名称末尾添加一个 "/"，
 * 如果是文件这显示最新修改时间。
 */
public class FileNameDecorator implements NameProvider<File> {

    private NameProvider<File> decorated;

    public FileNameDecorator(NameProvider<File> decorated) {
        this.decorated = decorated;
    }

    // 给文件树的名字加后缀，目录的结尾加上'/',文件的结尾加上时间戳。
    @Override
    public String getName(File node) {
        String txt = decorated.getName(node);
        if (node.isDirectory())
            return txt + "/";
        else {
            long lastModified = node.lastModified();
            // 将lastModified转换为年月日字符串
            String lastModifiedStr = new java.text.SimpleDateFormat("yyyy-MM-dd")
                    .format(new java.util.Date(lastModified));
            return txt + "[" + lastModifiedStr + "]";
        }

    }


    public static void main(String[] args) {
        NameProvider<File> provider = new StarDecorator(new FileNameDecorator(
                new FileNameProvider()));
        new StarDecorator(provider);

        VisualTreeViewer<File> viewer = new VisualTreeViewer<>(new DirTreeProvider(new File(".")), provider);
        viewer.show();
    }

}
