package com.basejava.storage;

import com.basejava.storage.ioStrategy.ObjectStreamIOStrategy;

import java.io.File;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {
    public ObjectStreamFileStorageTest() {
        super(new FileStorage(new File(STORAGE_DIR)).setIoStrategy(new ObjectStreamIOStrategy()));
    }
}
