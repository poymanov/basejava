package com.basejava.storage;

public class ObjectStreamUniversalPathStorageTest extends AbstractStorageTest {

    public ObjectStreamUniversalPathStorageTest() {
        super(new ObjectStreamUniversalPathStorage(STORAGE_DIR));
    }
}
