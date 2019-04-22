package com.basejava;

import java.io.File;

public class MainTestFile {

    public static void main(String[] args) {
        File file = new File("./");
        printData(file);
    }

    private static void printData(File file) {
        String[] list = file.list();

        if (list != null) {
            for (String item : list) {
                System.out.println(item);

                File file2 = new File(file.getPath() + "/" + item);

                if (file2.isDirectory()) {
                    printData(file2);
                }
            }
        } else {
            System.out.println(file.getName());
        }
    }
}
