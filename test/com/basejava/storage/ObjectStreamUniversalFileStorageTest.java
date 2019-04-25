package com.basejava.storage;

import java.io.File;

public class ObjectStreamUniversalFileStorageTest extends AbstractStorageTest {

    public ObjectStreamUniversalFileStorageTest() {
        super(new ObjectStreamUniversalFileStorage(new File(STORAGE_DIR)));
    }
}
