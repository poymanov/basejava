package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage<String> {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    protected String findSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void updateItem(String searchKey, Resume resume) {
        storage.put(searchKey, resume);
    }

    @Override
    protected void addItem(String searchKey, Resume resume) {
        storage.put(searchKey, resume);
    }

    @Override
    protected Resume getItem(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void removeItem(String searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected boolean isExist(String searchKey) {
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
