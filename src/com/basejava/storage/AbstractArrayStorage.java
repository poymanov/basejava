package com.basejava.storage;

import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int MAX_SIZE = 10000;
    protected Resume[] storage = new Resume[MAX_SIZE];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected void updateItem(String index, Resume resume) {
        int storageIndex = convertIndexType(index);
        storage[storageIndex] = resume;
    }

    protected void addItem(String index, Resume resume) {
        if (size == MAX_SIZE) {
            throw new StorageException("Overflow the maximum storage size (" + MAX_SIZE + ")", resume.getUuid());
        }

        addResume(resume, index);
        size++;
    }

    protected Resume getItem(String index) {
        int storageIndex = convertIndexType(index);
        return storage[storageIndex];
    }

    protected void removeItem(String index) {
        removeResume(index);
        storage[size - 1] = null;
        size--;
    }

    protected boolean isExists(String index) {
        return Integer.valueOf(index) >= 0;
    }

    protected abstract void addResume(Resume resume, String index);

    protected abstract void removeResume(String index);
}
