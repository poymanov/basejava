package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    protected String findIndex(Object uuid) {
        return uuid.toString();
    }

    @Override
    protected void updateItem(Object index, Resume resume) {
        storage.put(index.toString(), resume);
    }

    @Override
    protected void addItem(Object index, Resume resume) {
        storage.put(index.toString(), resume);
    }

    @Override
    protected Resume getItem(Object index) {
        return storage.get(index.toString());
    }

    @Override
    protected void removeItem(Object index) {
        storage.remove(index.toString());
    }

    @Override
    protected boolean isExists(Object index) {
        return storage.containsKey(index.toString());
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
