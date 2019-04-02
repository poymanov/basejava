package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected String findIndex(String uuid) {
        Resume searchResume = new Resume(uuid);
        int searchIndex = Arrays.binarySearch(storage, 0, size, searchResume);
        return String.valueOf(searchIndex);
    }

    @Override
    protected void addResume(Resume resume, String index) {
        int storageIndex = convertIndexType(index);
        storageIndex = -storageIndex - 1;
        System.arraycopy(storage, storageIndex, storage, storageIndex + 1, size - storageIndex);
        storage[storageIndex] = resume;
    }

    @Override
    protected void removeResume(String index) {
        int storageIndex = convertIndexType(index);
        int numbersMoved = size - storageIndex - 1;
        if (numbersMoved > 0) {
            System.arraycopy(storage, storageIndex + 1, storage, storageIndex, numbersMoved);
        }
    }
}
