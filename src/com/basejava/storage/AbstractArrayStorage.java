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

    protected boolean isExists(int index) {
        return index >= 0;
    }

    protected void updateItem(int index, Resume resume) {
        storage[index] = resume;
    }

    protected void addItem(int index, Resume resume) {
        if (size == MAX_SIZE) {
            throw new StorageException("Overflow the maximum storage size (" + MAX_SIZE + ")", resume.getUuid());
        }

        addResume(resume, index);
        size++;
    }

    protected Resume getItem(int index) {
        return storage[index];
    }

    protected void removeItem(int index) {
        removeResume(index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void addResume(Resume resume, int index);

    protected abstract void removeResume(int index);
}
