package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<Resume>();

    @Override
    protected boolean isExists(int index) {
        try {
            storage.get(index);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected int findIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    protected void updateItem(int index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void addItem(int index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getItem(int index) {
        return storage.get(index);
    }

    @Override
    protected void removeItem(int index) {
        storage.remove(index);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] data = new Resume[storage.size()];

        for (int i = 0; i < storage.size(); i++) {
            data[i] = storage.get(i);
        }

        return data;
    }
}
