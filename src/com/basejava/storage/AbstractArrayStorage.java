package com.basejava.storage;

import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int MAX_SIZE = 10000;
    protected Resume[] storage = new Resume[MAX_SIZE];
    protected int size;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    @Override
    protected void updateItem(Integer index, Resume resume) {
        storage[(int) index] = resume;
    }

    @Override
    protected void addItem(Integer index, Resume resume) {
        if (size == MAX_SIZE) {
            LOG.info(String.format("Overflow maximum storage - Resume(%s)", resume.getUuid()));
            throw new StorageException("Overflow the maximum storage size (" + MAX_SIZE + ")", resume.getUuid());
        }

        addResume(resume, (int) index);
        size++;
    }

    @Override
    protected Resume getItem(Integer index) {
        return storage[(int) index];
    }

    @Override
    protected void removeItem(Integer index) {
        removeResume((int) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    protected boolean isExist(Integer index) {
        return (int) index >= 0;
    }

    protected abstract void addResume(Resume resume, int index);

    protected abstract void removeResume(int index);
}
