package designpattern.adapter.tree.demo;

import java.io.File;

import designpattern.adapter.tree.NameProvider;
import designpattern.adapter.tree.SimpleTreeViewer;
import designpattern.adapter.tree.TreeContentProvider;
import designpattern.adapter.tree.VisualTreeViewer;

/**
 * <pre>
├── tree
│   ├── demo
│   │   ├── FolderTree.java
│   │   ├── OrgTree.java
│   │   ├── DirTreeProvider.java
│   │   ├── SimpleTree.java
│   │   └── FileNameProvider.java
│   ├── VisualTreeViewer.java
│   ├── TreeContentProvider.java
│   ├── README.md
│   ├── SimpleTreeViewer.java
│   └── NameProvider.java
└── livedemo
    └── TreeViewer.java
 * </pre>
 */
public class FolderTree {

    public static void main(String[] args) {

        // 获取当前目录作为文件(File)
        TreeContentProvider<File> cp = new DirTreeProvider(
                new File("./src/main/java/designpattern/adapter"));
        NameProvider<File> nameProvider = new NameProvider<File>() {
            @Override
            public String getName(File file) {
                return file.getName();
            }
        };

        new SimpleTreeViewer<File>(cp, nameProvider).show();
        new VisualTreeViewer<File>(cp, nameProvider).show();
    }
}
