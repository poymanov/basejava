package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object findIndex(Object uuid) {
        Resume searchResume = new Resume(uuid.toString());
        return Arrays.binarySearch(storage, 0, size, searchResume);
    }

    @Override
    protected void addResume(Resume resume, Object index) {
        int storageIndex = (int) index;
        storageIndex = -storageIndex - 1;
        System.arraycopy(storage, storageIndex, storage, storageIndex + 1, size - storageIndex);
        storage[storageIndex] = resume;
    }

    @Override
    protected void removeResume(Object index) {
        int storageIndex = (int) index;
        int numbersMoved = size - storageIndex - 1;
        if (numbersMoved > 0) {
            System.arraycopy(storage, storageIndex + 1, storage, storageIndex, numbersMoved);
        }
    }
}
