package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
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

        sort();
    }

    @Override
    public void delete(String uuid) {
        int resumeIndex = findIndex(uuid);

        if (resumeIndex == -1) {
            System.out.println("Resume is not found");
            return;
        }

        storage[resumeIndex] = storage[size - 1];
        storage[size - 1] = null;
        size--;

        sort();
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchResume = new Resume();
        searchResume.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchResume);
    }

    private void sort() {
        boolean isSorted = false;
        Resume tempResume;

        while (!isSorted) {
            isSorted = true;

            for (int i = 0; i < size - 1; i++) {
                int compareResult = storage[i].compareTo(storage[i + 1]);

                if (compareResult > 0) {
                    isSorted = false;
                    tempResume = storage[i];
                    storage[i] = storage[i + 1];
                    storage[i + 1] = tempResume;
                }
            }
        }
    }
}
