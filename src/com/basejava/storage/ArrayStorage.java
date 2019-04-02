package com.basejava.storage;

import com.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected String findIndex(String uuid) {
        int index = -1;

        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                index = i;
                break;
            }
        }

        return String.valueOf(index);
    }

    @Override
    protected void addResume(Resume resume, String index) {
        storage[size] = resume;
    }

    @Override
    protected void removeResume(String index) {
        int storageIndex = convertIndexType(index);
        storage[storageIndex] = storage[size - 1];
    }
}
