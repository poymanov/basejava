package com.basejava.storage;

import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }

        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }

        this.directory = directory;
    }

    @Override
    protected File findSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void updateItem(File file, Resume resume) {
        saveFile(file, resume);
    }

    @Override
    protected void addItem(File file, Resume resume) {
        try {
            file.createNewFile();
            saveFile(file, resume);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume getItem(File file) {
        getFileContent(file);
        return new Resume("test");
    }

    @Override
    protected void removeItem(File file) {
        file.delete();
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> getAll() {
        ArrayList<Resume> resumes = new ArrayList<>();
        String[] files = directory.list();

        if (files != null) {
            for (String item : files) {
                getFileContent(new File(item));
            }
        }

        return resumes;
    }

    protected abstract void saveFile(File file, Resume resume);

    protected abstract void getFileContent(File file);

    @Override
    public void clear() {
        String[] files = directory.list();
        if (files != null) {
            for (String item : files) {
                File file = new File(item);
                file.delete();
            }
        }
    }
}
