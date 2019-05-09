package com.basejava.storage;

import com.basejava.storage.ioStrategy.ObjectStreamIOStrategy;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR).setIoStrategy(new ObjectStreamIOStrategy()));
    }
}
