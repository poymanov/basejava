package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.List;

public interface Storage {
    void update(Resume resume);

    void clear();

    void save(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    List<Resume> getAllSorted();
}
