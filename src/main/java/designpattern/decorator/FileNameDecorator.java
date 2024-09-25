package designpattern.decorator;

import java.io.File;

import designpattern.adapter.tree.NameProvider;
import designpattern.adapter.tree.VisualTreeVIewer;
import designpattern.adapter.tree.demo.DirTreeProvider;
import designpattern.adapter.tree.demo.FileNameProvider;

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
        if (node.isDirectory())
            return decorated.getName(node) + "/";
        else {
            long lastModified = node.lastModified();
            // 将lastModified转换为年月日字符串
            String lastModifiedStr = new java.text.SimpleDateFormat("yyyy-MM-dd")
                    .format(new java.util.Date(lastModified));
            return decorated.getName(node) + "[" + lastModifiedStr + "]";
        }

    }

    public static void main(String[] args) {
        NameProvider<File> provider = new FileNameDecorator(
                new FileNameProvider());

        VisualTreeVIewer<File> viewer = new VisualTreeVIewer<>(new DirTreeProvider(new File(".")), provider);
        viewer.show();
    }

}
