package com.basejava;

import com.basejava.model.Resume;
import com.basejava.storage.SortedArrayStorage;
import com.basejava.storage.Storage;

public class MainTestSortedArrayStorage {
    private static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        Resume r3 = new Resume("uuid3");
        Resume r4 = new Resume("uuid3");

        ARRAY_STORAGE.save(r4);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r1);
        printAll();

        ARRAY_STORAGE.delete(r4.getUuid());
        printAll();
    }

    private static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
