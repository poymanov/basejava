package com.basejava.storage;

import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractUniversalPathStorage extends AbstractStorage<Path> {
    private Path directory;
    private IOStrategy ioStrategy;

    protected AbstractUniversalPathStorage(String dir) {
        directory = Paths.get(dir);

        Objects.requireNonNull(directory, "directory must not be null");

        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not readable/writable");
        }
    }

    public void setIoStrategy(IOStrategy ioStrategy) {
        this.ioStrategy = ioStrategy;
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected void updateItem(Path path, Resume resume) {
        saveFile(path, resume);
    }

    @Override
    protected void addItem(Path path, Resume resume) {
        try {
            Files.createFile(path);
            saveFile(path, resume);
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected Resume getItem(Path path) {
        try {
            return ioStrategy.input(path.toFile());
        } catch (Exception e) {
            throw new StorageException("File read error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void removeItem(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't delete path " + path.toAbsolutePath(), path.getFileName().toString(), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected List<Resume> getAll() {
        try (Stream<Path> paths = Files.walk(Paths.get(directory.toAbsolutePath().toString()))) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(this::getItem)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new StorageException("Couldn't get all resumes " + directory.toAbsolutePath().toString(), null, e);
        }
    }

    protected void saveFile(Path path, Resume resume) {
        try {
            ioStrategy.output(resume, path.toFile());
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory.toAbsolutePath()).forEach(this::delete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", directory.toAbsolutePath().toString(), e);
        }
    }

    public int size() {
        try {
            return (int) Files.list(directory.toAbsolutePath()).count();
        } catch (IOException e) {
            throw new StorageException("Files count error", directory.toAbsolutePath().toString(), e);
        }
    }

    private void delete(Path path) {
        try {
            Files.delete(path.toAbsolutePath());
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.getFileName().toString());
        }
    }
}
