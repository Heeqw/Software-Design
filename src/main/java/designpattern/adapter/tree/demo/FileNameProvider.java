package designpattern.adapter.tree.demo;

import java.io.File;

import designpattern.adapter.tree.NameProvider;

public class FileNameProvider implements NameProvider<File> {

    @Override
    public String getName(File file) {
        return file.getName();
    }
}