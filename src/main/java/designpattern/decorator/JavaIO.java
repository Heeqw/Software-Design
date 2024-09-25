package designpattern.decorator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

public class JavaIO {
    public static void main(String[] args) throws IOException {
        InputStream fs = new GZIPInputStream(
                new BufferedInputStream(
                        new FileInputStream(
                                new File("/Users/zhangtiange/Downloads/application.log.gz"))));

        try (InputStreamReader reader = new InputStreamReader(fs, "UTF-8")) {
            while (reader.ready())
                System.out.print((char) reader.read());
        }

    }
}
