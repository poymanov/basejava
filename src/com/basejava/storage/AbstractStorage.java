package com.basejava.storage;

import com.basejava.exceptions.ExistedStorageException;
import com.basejava.exceptions.NotExistedStorageException;
import com.basejava.model.Resume;

import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    protected static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public void update(Resume resume) {
        SK searchKey = getExistedSearchKey(resume.getUuid());
        updateItem(searchKey, resume);
    }

    @Override
    public void save(Resume resume) {
        SK searchKey = getNotExistedSearchKey(resume.getUuid());
        addItem(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        SK searchKey = getExistedSearchKey(uuid);
        return getItem(searchKey);
    }

    @Override
    public void delete(String uuid) {
        SK searchKey = getExistedSearchKey(uuid);
        removeItem(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = getAll();
        resumes.sort(Resume::compareTo);
        return resumes;
    }

    protected abstract SK findSearchKey(String uuid);

    protected abstract void updateItem(SK searchKey, Resume resume);

    protected abstract void addItem(SK searchKey, Resume resume);

    protected abstract Resume getItem(SK searchKey);

    protected abstract void removeItem(SK searchKey);

    protected abstract boolean isExist(SK searchKey);

    protected abstract List<Resume> getAll();

    private SK getExistedSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);

        if (!isExist(searchKey)) {
            LOG.info(String.format("Resume (%s) not exists", uuid));
            throw new NotExistedStorageException(uuid);
        }

        return searchKey;
    }

    private SK getNotExistedSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);

        if (isExist(searchKey)) {
            LOG.info(String.format("Resume (%s) already exists", uuid));
            throw new ExistedStorageException(uuid);
        }

        return searchKey;
    }
}
