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
    protected void addResume(Resume resume) {
        int searchResult = Arrays.binarySearch(storage, 0, size, resume);
        int index = Math.abs(searchResult) - 1;

        System.arraycopy(storage, index, storage, index + 1, size);
        storage[index] = resume;
        size++;
    }

    @Override
    protected void removeResume(int resumeIndex) {
        System.arraycopy(storage, resumeIndex + 1, storage, resumeIndex, size);
        size--;
    }
}
