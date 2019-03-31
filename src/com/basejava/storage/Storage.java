package com.basejava.storage;

import com.basejava.model.Resume;

public interface Storage {
    void update(Resume resume);

    void clear();

    void save(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    Resume[] getAll();
}
