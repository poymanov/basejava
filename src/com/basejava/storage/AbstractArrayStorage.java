package com.basejava.storage;

import com.basejava.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int MAX_SIZE = 10000;
    protected Resume[] storage = new Resume[MAX_SIZE];
    protected int size;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int resumeIndex = findIndex(uuid);

        if (resumeIndex == -1) {
            System.out.println("Resume is not found");
            return null;
        }

        return storage[resumeIndex];
    }

    protected abstract int findIndex(String uuid);
}
