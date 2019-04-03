package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer findIndex(Object uuid) {
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

    public List<Resume> getAllSorted() {
        List<Resume> resumes = Arrays.asList(Arrays.copyOf(storage, size));
        resumes.sort(Comparator.comparing(Resume::getUuid));
        return resumes;
    }
}
