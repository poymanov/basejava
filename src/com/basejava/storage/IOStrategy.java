package com.basejava.storage;

import com.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IOStrategy {
    void output(Resume resume, OutputStream os) throws IOException;

    Resume input(InputStream is) throws IOException;
}
