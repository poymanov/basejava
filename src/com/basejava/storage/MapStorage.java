package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    protected String findIndex(String uuid) {
        return uuid;
    }

    @Override
    protected void updateItem(String index, Resume resume) {
        storage.put(index, resume);
    }

    @Override
    protected void addItem(String index, Resume resume) {
        storage.put(index, resume);
    }

    @Override
    protected Resume getItem(String index) {
        return storage.get(index);
    }

    @Override
    protected void removeItem(String index) {
        storage.remove(index);
    }

    @Override
    protected boolean isExists(String index) {
        return storage.containsKey(index);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }
}
