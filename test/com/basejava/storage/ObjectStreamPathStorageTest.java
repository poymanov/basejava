package com.basejava.storage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR).setIoStrategy(new ObjectStreamIOStrategy()));
    }
}
