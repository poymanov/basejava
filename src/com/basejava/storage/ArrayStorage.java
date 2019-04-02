package com.basejava.storage;

import com.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object findIndex(Object uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid.toString())) {
                return i;
            }
        }

        return -1;
    }

    @Override
    protected void addResume(Resume resume, Object index) {
        storage[size] = resume;
    }

    @Override
    protected void removeResume(Object index) {
        storage[(int) index] = storage[size - 1];
    }
}
