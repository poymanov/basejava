package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    protected String findSearchKey(Object uuid) {
        return (String) uuid;
    }

    @Override
    protected void updateItem(Object searchKey, Resume resume) {
        storage.put((String) searchKey, resume);
    }

    @Override
    protected void addItem(Object searchKey, Resume resume) {
        storage.put((String) searchKey, resume);
    }

    @Override
    protected Resume getItem(Object searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void removeItem(Object searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> getAll() {
        return Arrays.asList(storage.values().toArray(new Resume[0]));
    }
}
