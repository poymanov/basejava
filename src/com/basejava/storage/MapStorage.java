package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.*;

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
    public List<Resume> getAllSorted() {
        List<Resume> resumes = Arrays.asList(storage.values().toArray(new Resume[0]));
        resumes.sort(Comparator.comparing(Resume::getUuid));
        return resumes;
    }
}
