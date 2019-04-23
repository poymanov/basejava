package com.basejava;

import java.io.File;

public class MainTestFile {

    public static void main(String[] args) {
        File file = new File("./");
        printData(file);
    }

    private static void printData(File root) {
        File[] files = root.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    printData(file);
                }

                System.out.println(file.getName());
            }
        } else {
            System.out.println(root.getName());
        }
    }
}
