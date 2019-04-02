package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<Resume>();

    @Override
    protected String findIndex(String uuid) {
        int index = -1;

        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                index = i;
                break;
            }
        }

        return String.valueOf(index);
    }

    @Override
    protected void updateItem(String index, Resume resume) {
        int storageIndex = convertIndexType(index);
        storage.set(storageIndex, resume);
    }

    @Override
    protected void addItem(String index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getItem(String index) {
        int storageIndex = convertIndexType(index);
        return storage.get(storageIndex);
    }

    @Override
    protected void removeItem(String index) {
        int storageIndex = convertIndexType(index);
        storage.remove(storageIndex);
    }

    protected boolean isExists(String index) {
        return Integer.valueOf(index) >= 0;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }
}
