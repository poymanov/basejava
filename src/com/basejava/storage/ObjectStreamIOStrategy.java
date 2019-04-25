package com.basejava.storage;

import com.basejava.model.Resume;

import java.io.*;

public class ObjectStreamIOStrategy implements IOStrategy {
    @Override
    public void output(Resume resume, File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(getOutputStream(file))) {
            oos.writeObject(resume);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file");
        }
    }

    @Override
    public Resume input(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(getInputStream(file))) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException("Failed to read file");
        }
    }

    private OutputStream getOutputStream(File file) throws FileNotFoundException {
        return new BufferedOutputStream(new FileOutputStream(file));
    }

    private InputStream getInputStream(File file) throws FileNotFoundException {
        return new BufferedInputStream(new FileInputStream(file));
    }
}
