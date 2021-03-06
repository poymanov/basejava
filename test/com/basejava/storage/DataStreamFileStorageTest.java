package com.basejava.storage;

import com.basejava.storage.ioStrategy.DataStreamIOStrategy;

import java.io.File;

public class DataStreamFileStorageTest extends AbstractStorageTest {
    public DataStreamFileStorageTest() {
        super(new FileStorage(new File(STORAGE_DIR)).setIoStrategy(new DataStreamIOStrategy()));
    }
}
