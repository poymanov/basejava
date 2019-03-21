package com.basejava.storage;

import com.basejava.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int maxSize = 10000;
    private Resume[] storage = new Resume[maxSize];
    private int size;

    public void update(Resume resume) {
        if (findResumeByUuid(resume.getUuid()) == null) {
            printNotFound();
            return;
        }

        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                storage[i] = resume;
            }
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, size(), null);
        size = 0;
    }

    public void save(Resume resume) {
        if (findResumeByUuid(resume.getUuid()) != null) {
            printDuplicate();
            return;
        }

        int currentSize = size;

        if (++currentSize > maxSize) {
            printOverflowMaxSize();
            return;
        }

        storage[size] = resume;
        size++;
    }

    public Resume get(String uuid) {
        Resume resume = findResumeByUuid(uuid);

        if (resume == null) {
            printNotFound();
            return null;
        }

        return resume;
    }

    public void delete(String uuid) {
        if (findResumeByUuid(uuid) == null) {
            printNotFound();
            return;
        }

        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
            }
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private void printNotFound() {
        System.out.println("Resume is not found");
    }

    private void printDuplicate() {
        System.out.println("Resume already exists");
    }

    private void printOverflowMaxSize() {
        System.out.println("Overflow the maximum storage size (" + maxSize + ")");
    }

    private Resume findResumeByUuid(String uuid) {
        for (Resume resume: getAll()) {
            if (resume.getUuid().equals(uuid)) {
                return resume;
            }
        }

        return null;
    }
}
