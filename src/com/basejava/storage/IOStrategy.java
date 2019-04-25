package com.basejava.storage;

import com.basejava.model.Resume;

import java.io.File;
import java.io.FileNotFoundException;

public interface IOStrategy {
    void output(Resume resume, File file) throws FileNotFoundException;

    Resume input(File file);
}
