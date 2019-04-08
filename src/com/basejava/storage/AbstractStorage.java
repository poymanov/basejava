package com.basejava.storage;

import com.basejava.exceptions.ExistedStorageException;
import com.basejava.exceptions.NotExistedStorageException;
import com.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    public void update(Resume resume) {
        Object resumeSearchKey = getExistedSearchKey(resume.getUuid());
        updateItem(resumeSearchKey, resume);
    }

    public void save(Resume resume) {
        Object resumeSearchKey = getNotExistedSearchKey(resume.getUuid());
        addItem(resumeSearchKey, resume);
    }

    public Resume get(String uuid) {
        Object resumeSearchKey = getExistedSearchKey(uuid);
        return getItem(resumeSearchKey);
    }

    public void delete(String uuid) {
        Object resumeSearchKey = getExistedSearchKey(uuid);
        removeItem(resumeSearchKey);
    }

    public List<Resume> getAllSorted() {
        List<Resume> resumes = getAll();
        resumes.sort(Comparator.comparing(Resume::getUuid));
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
