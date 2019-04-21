package com.basejava;

import java.io.File;
import java.util.Arrays;

public class MainTestFile {
    public static void main(String[] args) {
        String root = "./";
        printData(root, 0);
    }

    private static void printData(String path, int depth) {
        File file = new File(path);

        String[] list = file.list();

        if (list != null) {
            for (String item : list) {

                if (item.equals(".idea") || item.equals(".git")) {
                    continue;
                }

                for (int i = 0; i < depth; i++) {
                    System.out.print('-');
                }

                System.out.println(item);

                File file2 = new File(path + "/" + item);

                if (file2.isDirectory()) {
                    printData(file2.getPath(), ++depth);
                }
            }
        } else {
            System.out.println(file.getName());
        }

    }
}
