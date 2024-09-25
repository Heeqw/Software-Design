package designpattern.adapter.tree.demo;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import designpattern.adapter.tree.NameProvider;
import designpattern.adapter.tree.SimpleTreeViewer;
import designpattern.adapter.tree.TreeContentProvider;
import designpattern.adapter.tree.VisualTreeVIewer;

public class FolderTree {

    public static void main(String[] args) {

        // 获取当前目录作为文件(File)
        File currentDir = new File("./src/main/java/designpattern");

        TreeContentProvider<File> cp =

                new TreeContentProvider<File>() {
                    @Override
                    public List<File> getRoots() {
                        return Arrays.asList(currentDir.listFiles());
                    }

                    @Override
                    public List<File> getChildren(File parent) {
                        if (parent.isDirectory())
                            return Arrays.asList(parent.listFiles());
                        else
                            return List.of();

                    }
                };
        NameProvider<File> nameProvider = new NameProvider<File>() {
            @Override
            public String getName(File file) {
                return file.getName();
            }
        };

        new SimpleTreeViewer<File>(cp, nameProvider).show();
        new VisualTreeVIewer<File>(cp, nameProvider).show();
    }
}
