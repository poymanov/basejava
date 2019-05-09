package com.basejava.storage;

import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;
import com.basejava.storage.ioStrategy.IOStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private File directory;
    private IOStrategy ioStrategy;

    public FileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }

        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }

        this.directory = directory;
    }

    public Storage setIoStrategy(IOStrategy ioStrategy) {
        this.ioStrategy = ioStrategy;

        return this;
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
        try {
            return ioStrategy.input(new BufferedInputStream(new FileInputStream(file)));
        } catch (Exception e) {
            throw new StorageException("File read error", file.getName(), e);
        }
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
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                resumes.add(getItem(file));
            }
        }

        return resumes;
    }

    protected void saveFile(File file, Resume resume) {
        try {
            ioStrategy.output(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    public int size() {
        String[] files = directory.list();

        if (files == null) {
            throw new StorageException("Directory read error", null);
        }

        return files.length;
    }
}
