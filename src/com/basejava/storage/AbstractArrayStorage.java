package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int MAX_SIZE = 10000;
    protected Resume[] storage = new Resume[MAX_SIZE];
    protected int size;

    public void update(Resume resume) {
        int resumeIndex = findIndex(resume.getUuid());

        if (!isExists(resumeIndex)) {
            System.out.println("Resume is not found");
            return;
        }

        storage[resumeIndex] = resume;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        int resumeIndex = findIndex(resume.getUuid());

        if (isExists(resumeIndex)) {
            System.out.println("Resume already exists");
            return;
        }

        if (size == MAX_SIZE) {
            System.out.println("Overflow the maximum storage size (" + MAX_SIZE + ")");
            return;
        }

        addResume(resume, resumeIndex);
    }

    public Resume get(String uuid) {
        int resumeIndex = findIndex(uuid);

        if (!isExists(resumeIndex)) {
            System.out.println("Resume is not found");
            return null;
        }

        return storage[resumeIndex];
    }

    public void delete(String uuid) {
        int resumeIndex = findIndex(uuid);

        if (!isExists(resumeIndex)) {
            System.out.println("Resume is not found");
            return;
        }

        removeResume(resumeIndex);
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public void increaseSize() {
        size++;
    }

    public void reduceSize() {
        size--;
    }

    private boolean isExists(int index) {
        return index >= 0;
    }

    protected abstract int findIndex(String uuid);

    protected abstract void addResume(Resume resume, int index);

    protected abstract void removeResume(int index);
}
