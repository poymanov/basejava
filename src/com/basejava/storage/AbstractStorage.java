package com.basejava.storage;

import com.basejava.exceptions.ExistedStorageException;
import com.basejava.exceptions.NotExistedStorageException;
import com.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {
    public void update(Resume resume) {
        Object resumeIndex = findIndex(resume.getUuid());

        if (!isExists(resumeIndex)) {
            throw new NotExistedStorageException(resume.getUuid());
        }

        updateItem(resumeIndex, resume);
    }

    public void save(Resume resume) {
        Object resumeIndex = findIndex(resume.getUuid());

        if (isExists(resumeIndex)) {
            throw new ExistedStorageException(resume.getUuid());
        }

        addItem(resumeIndex, resume);
    }

    public Resume get(String uuid) {
        Object resumeIndex = findIndex(uuid);

        if (!isExists(resumeIndex)) {
            throw new NotExistedStorageException(uuid);
        }

        return getItem(resumeIndex);
    }

    public void delete(String uuid) {
        Object resumeIndex = findIndex(uuid);

        if (!isExists(resumeIndex)) {
            throw new NotExistedStorageException(uuid);
        }

        removeItem(resumeIndex);
    }

    protected abstract Object findIndex(Object uuid);

    protected abstract void updateItem(Object index, Resume resume);

    protected abstract void addItem(Object index, Resume resume);

    protected abstract Resume getItem(Object index);

    protected abstract void removeItem(Object index);

    protected abstract boolean isExists(Object index);
}
