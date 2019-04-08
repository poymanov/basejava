package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.*;

public class MapHashStorage extends AbstractStorage {
    protected Map<Object, Resume> storage = new HashMap<>();

    @Override
    protected Object findSearchKey(Object uuid) {
        return Objects.hash(uuid);
    }

    @Override
    protected void updateItem(Object searchKey, Resume resume) {
        storage.put(searchKey, resume);
    }

    @Override
    protected void addItem(Object searchKey, Resume resume) {
        storage.put(searchKey, resume);
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
