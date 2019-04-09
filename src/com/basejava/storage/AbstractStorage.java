package com.basejava.storage;

import com.basejava.exceptions.ExistedStorageException;
import com.basejava.exceptions.NotExistedStorageException;
import com.basejava.model.Resume;

import java.util.List;

public abstract class AbstractStorage implements Storage {
    @Override
    public void update(Resume resume) {
        Object searchKey = getExistedSearchKey(resume.getUuid());
        updateItem(searchKey, resume);
    }

    @Override
    public void save(Resume resume) {
        Object searchKey = getNotExistedSearchKey(resume.getUuid());
        addItem(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistedSearchKey(uuid);
        return getItem(searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistedSearchKey(uuid);
        removeItem(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = getAll();
        resumes.sort(Resume::compareTo);
        return resumes;
    }

    protected abstract Object findSearchKey(Object uuid);

    protected abstract void updateItem(Object searchKey, Resume resume);

    protected abstract void addItem(Object searchKey, Resume resume);

    protected abstract Resume getItem(Object searchKey);

    protected abstract void removeItem(Object searchKey);

    protected abstract boolean isExist(Object searchKey);

    protected abstract List<Resume> getAll();

    private Object getExistedSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);

        if (!isExist(searchKey)) {
            throw new NotExistedStorageException(uuid);
        }

        return searchKey;
    }

    private Object getNotExistedSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);

        if (isExist(searchKey)) {
            throw new ExistedStorageException(uuid);
        }

        return searchKey;
    }
}
