package com.basejava.storage;

import com.basejava.exceptions.ExistedStorageException;
import com.basejava.exceptions.NotExistedStorageException;
import com.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {
    public void update(Resume resume) {
        int resumeIndex = findIndex(resume.getUuid());

        if (!isExists(resumeIndex)) {
            throw new NotExistedStorageException(resume.getUuid());
        }

        updateItem(resumeIndex, resume);
    }

    public void save(Resume resume) {
        int resumeIndex = findIndex(resume.getUuid());

        if (isExists(resumeIndex)) {
            throw new ExistedStorageException(resume.getUuid());
        }

        addItem(resumeIndex, resume);
    }

    public Resume get(String uuid) {
        int resumeIndex = findIndex(uuid);

        if (!isExists(resumeIndex)) {
            throw new NotExistedStorageException(uuid);
        }

        return getItem(resumeIndex);
    }

    public void delete(String uuid) {
        int resumeIndex = findIndex(uuid);

        if (!isExists(resumeIndex)) {
            throw new NotExistedStorageException(uuid);
        }

        removeItem(resumeIndex);
    }

    protected abstract boolean isExists(int index);

    protected abstract int findIndex(String uuid);

    protected abstract void updateItem(int index, Resume resume);

    protected abstract void addItem(int index, Resume resume);

    protected abstract Resume getItem(int index);

    protected abstract void removeItem(int index);
}
