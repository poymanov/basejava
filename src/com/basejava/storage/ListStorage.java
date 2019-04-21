package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    protected Integer findSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid.toString())) {
                return i;
            }
        }

        return -1;
    }

    @Override
    protected void updateItem(Integer index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void addItem(Integer index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getItem(Integer index) {
        return storage.get(index);
    }

    @Override
    protected void removeItem(Integer index) {
        storage.remove(index.intValue());
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
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
