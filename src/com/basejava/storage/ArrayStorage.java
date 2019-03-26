package com.basejava.storage;

import com.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    protected void addResume(Resume resume) {
        storage[size] = resume;
        size++;
    }

    @Override
    protected void removeResume(int resumeIndex) {
        storage[resumeIndex] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }
}
