package com.basejava.storage;

import com.basejava.exceptions.ExistedStorageException;
import com.basejava.exceptions.NotExistedStorageException;
import com.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {
    public void update(Resume resume) {
        String resumeIndex = findIndex(resume.getUuid());

        if (!isExists(resumeIndex)) {
            throw new NotExistedStorageException(resume.getUuid());
        }

        updateItem(resumeIndex, resume);
    }

    public void save(Resume resume) {
        String resumeIndex = findIndex(resume.getUuid());

        if (isExists(resumeIndex)) {
            throw new ExistedStorageException(resume.getUuid());
        }

        addItem(resumeIndex, resume);
    }

    public Resume get(String uuid) {
        String resumeIndex = findIndex(uuid);

        if (!isExists(resumeIndex)) {
            throw new NotExistedStorageException(uuid);
        }

        return getItem(resumeIndex);
    }

    public void delete(String uuid) {
        String resumeIndex = findIndex(uuid);

        if (!isExists(resumeIndex)) {
            throw new NotExistedStorageException(uuid);
        }

        removeItem(resumeIndex);
    }

    protected int convertIndexType(String index) {
        return Integer.valueOf(index);
    }

    protected abstract String findIndex(String uuid);

    protected abstract void updateItem(String index, Resume resume);

    protected abstract void addItem(String index, Resume resume);

    protected abstract Resume getItem(String index);

    protected abstract void removeItem(String index);

    protected abstract boolean isExists(String index);
}
