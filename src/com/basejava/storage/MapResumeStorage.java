package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume findSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void updateItem(Resume searchResume, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void addItem(Resume searchResume, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getItem(Resume searchResume) {
        return searchResume;
    }

    @Override
    protected void removeItem(Resume searchResume) {
        storage.remove(searchResume.getUuid());
    }

    @Override
    protected boolean isExist(Resume resume) {
        return resume != null;
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
