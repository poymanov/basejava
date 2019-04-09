package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    protected Integer findSearchKey(Object uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid.toString())) {
                return i;
            }
        }

        return -1;
    }

    @Override
    protected void updateItem(Object index, Resume resume) {
        storage.set((int) index, resume);
    }

    @Override
    protected void addItem(Object index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getItem(Object index) {
        return storage.get((int) index);
    }

    @Override
    protected void removeItem(Object index) {
        storage.remove((int) index);
    }

    @Override
    protected boolean isExist(Object index) {
        return (int) index >= 0;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>(storage);
    }
}
