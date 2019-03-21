package com.basejava.storage;

import com.basejava.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size(), null);
        size = 0;
    }

    public void save(Resume resume) {
        storage[size] = resume;
        size++;
    }

    public Resume get(String uuid) {
        for (Resume resume: getAll()) {
            if (resume.getUuid().equals(uuid)) {
                return resume;
            }
        }

        return null;
    }

    public void delete(String uuid) {
        int deletedIndex = -1;

        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                deletedIndex = i;
                break;
            }
        }

        if (deletedIndex < 0) {
            return;
        }

        storage[deletedIndex] = null;

        for (int i = 0; i < size; i++) {
            storage[i] = storage[i + 1];
        }

        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
