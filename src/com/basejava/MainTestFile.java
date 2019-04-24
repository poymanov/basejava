package com.basejava;

import java.io.File;

public class MainTestFile {

    public static void main(String[] args) {
        File file = new File("./");
        printData(file, 0);
    }

    private static void printData(File root, int depth) {
        File[] files = root.listFiles();
        String indent = new String(new char[depth]).replace("\0", " ");
        depth++;

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println(indent + file.getName());
                    printData(file, depth);
                } else {
                    System.out.println(indent + file.getName());
                }
            }
        } else {
            System.out.println(indent + root.getName());
        }
    }
}
