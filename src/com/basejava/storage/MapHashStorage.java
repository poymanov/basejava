package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.*;

public class MapHashStorage extends AbstractStorage<Integer> {
    protected Map<Integer, Resume> storage = new HashMap<>();

    @Override
    protected Integer findSearchKey(String uuid) {
        return Objects.hash(uuid);
    }

    @Override
    protected void updateItem(Integer searchKey, Resume resume) {
        storage.put(searchKey, resume);
    }

    @Override
    protected void addItem(Integer searchKey, Resume resume) {
        storage.put(searchKey, resume);
    }

    @Override
    protected Resume getItem(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void removeItem(Integer searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected boolean isExist(Integer searchKey) {
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
