package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int findIndex(String uuid) {
        Resume searchResume = new Resume();
        searchResume.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchResume);
    }

    @Override
    protected void addResume(Resume resume, int index) {
        index = -index - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    @Override
    protected void removeResume(int index) {
        int numbersMoved = size - index - 1;
        if (numbersMoved > 0) {
            System.arraycopy(storage, index + 1, storage, index, numbersMoved);
        }
    }
}
