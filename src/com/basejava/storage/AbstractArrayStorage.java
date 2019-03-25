package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int MAX_SIZE = 10000;
    protected Resume[] storage = new Resume[MAX_SIZE];
    protected int size;

    public void update(Resume resume) {
        int resumeIndex = findIndex(resume.getUuid());

        if (resumeIndex == -1) {
            System.out.println("Resume is not found");
            return;
        }

        storage[resumeIndex] = resume;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume get(String uuid) {
        int resumeIndex = findIndex(uuid);

        if (resumeIndex == -1) {
            System.out.println("Resume is not found");
            return null;
        }

        return storage[resumeIndex];
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected abstract int findIndex(String uuid);
}
