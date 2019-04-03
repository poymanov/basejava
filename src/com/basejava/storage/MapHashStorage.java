package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.*;

public class MapHashStorage extends AbstractStorage {
    protected Map<Object, Resume> storage = new HashMap<>();

    @Override
    protected Object findIndex(Object uuid) {
        return Objects.hash(uuid);
    }

    @Override
    protected void updateItem(Object index, Resume resume) {
        storage.put(index, resume);
    }

    @Override
    protected void addItem(Object index, Resume resume) {
        storage.put(index, resume);
    }

    @Override
    protected Resume getItem(Object index) {
        return storage.get(index);
    }

    @Override
    protected void removeItem(Object index) {
        storage.remove(index);
    }

    @Override
    protected boolean isExists(Object index) {
        return storage.containsKey(index);
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
