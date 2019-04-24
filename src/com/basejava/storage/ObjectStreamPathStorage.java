package com.basejava.storage;

import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;

import java.io.*;

public class ObjectStreamPathStorage extends AbstractPathStorage {
    protected ObjectStreamPathStorage(String directory) {
        super(directory);
    }

    @Override
    protected void write(Resume resume, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        }
    }

    @Override
    protected Resume read(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
