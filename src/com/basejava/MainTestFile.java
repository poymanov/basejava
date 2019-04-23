package com.basejava;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainTestFile {

    public static void main(String[] args) {
        File file = new File("./");
        printData(file);
    }

    private static void printData(File file) {
        File[] list = file.listFiles();

        if (list != null) {
            for (File item : list) {
                System.out.println(getFilename(item));

                if (item.isDirectory()) {
                    printData(item);
                }
            }
        } else {
            System.out.println(getFilename(file));
        }
    }

    private static String getFilename(File file)
    {
        Path filepath = Paths.get(file.getAbsolutePath());
        return filepath.getFileName().toString();
    }
}
