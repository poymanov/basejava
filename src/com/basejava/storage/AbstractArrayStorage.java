package com.basejava.storage;

import com.basejava.exceptions.ExistedStorageException;
import com.basejava.exceptions.NotExistedStorageException;
import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int MAX_SIZE = 10000;
    protected Resume[] storage = new Resume[MAX_SIZE];
    protected int size;

    public void update(Resume resume) {
        int resumeIndex = findIndex(resume.getUuid());

        if (!isExists(resumeIndex)) {
            throw new NotExistedStorageException(resume.getUuid());
        }

        storage[resumeIndex] = resume;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        int resumeIndex = findIndex(resume.getUuid());

        if (isExists(resumeIndex)) {
            throw new ExistedStorageException(resume.getUuid());
        }

        if (size == MAX_SIZE) {
            throw new StorageException("Overflow the maximum storage size (" + MAX_SIZE + ")", resume.getUuid());
        }

        addResume(resume, resumeIndex);
        size++;
    }

    public Resume get(String uuid) {
        int resumeIndex = findIndex(uuid);

        if (!isExists(resumeIndex)) {
            throw new NotExistedStorageException(uuid);
        }

        return storage[resumeIndex];
    }

    public void delete(String uuid) {
        int resumeIndex = findIndex(uuid);

        if (!isExists(resumeIndex)) {
            throw new NotExistedStorageException(uuid);
        }

        removeResume(resumeIndex);
        storage[size - 1] = null;
        size--;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private boolean isExists(int index) {
        return index >= 0;
    }

    protected abstract int findIndex(String uuid);

    protected abstract void addResume(Resume resume, int index);

    protected abstract void removeResume(int index);
}
