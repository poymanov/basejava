package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer findSearchKey(Object uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid.toString())) {
                return i;
            }
        }

        return -1;
    }

    @Override
    protected void addResume(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    protected void removeResume(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }
}
