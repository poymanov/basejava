package com.basejava.model;

import com.basejava.storage.AbstractStorage;

import java.io.DataOutputStream;
import java.io.IOException;

public interface DataWriter {
    void write(DataOutputStream dos, Enum key, Object value) throws IOException;
}
