package com.basejava.storage;

import com.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

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

    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }

        return -1;
    }
}
