package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int MAX_SIZE = 10000;
    private Resume[] storage = new Resume[MAX_SIZE];
    private int size;

    public void update(Resume resume) {
        int resumeIndex = findIndex(resume.getUuid());

        if (resumeIndex == -1) {
            System.out.println("Resume is not found");
            return;
        }

        storage[resumeIndex] = resume;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        int resumeIndex = findIndex(resume.getUuid());

        if (resumeIndex >= 0) {
            System.out.println("Resume already exists");
            return;
        }

        if (size == MAX_SIZE) {
            System.out.println("Overflow the maximum storage size (" + MAX_SIZE + ")");
            return;
        }

        storage[size] = resume;
        size++;
    }

    public Resume get(String uuid) {
        int resumeIndex = findIndex(uuid);

        if (resumeIndex == -1) {
            System.out.println("Resume is not found");
            return null;
        }

        return storage[resumeIndex];
    }

    public void delete(String uuid) {
        int resumeIndex = findIndex(uuid);

        if (resumeIndex == -1) {
            System.out.println("Resume is not found");
            return;
        }

        storage[resumeIndex] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }

        return -1;
    }
}
