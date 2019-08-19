package me.huding.common.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileTest {
    public static void main(String[] args) throws IOException {
        Path dir = Files.createTempDirectory(null);
        System.out.println(dir);

        Path path = Files.createTempFile(null, null);

        System.out.println(path);
    }
}
