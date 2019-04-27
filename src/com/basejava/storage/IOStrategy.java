package com.basejava.storage;

import com.basejava.model.Resume;

import java.io.InputStream;
import java.io.OutputStream;

public interface IOStrategy {
    void output(Resume resume, OutputStream os);

    Resume input(InputStream is);
}
