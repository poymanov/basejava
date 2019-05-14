package com.basejava.model;

import java.io.IOException;

public interface DataWriter <T> {
    void write(T item) throws IOException;
}
