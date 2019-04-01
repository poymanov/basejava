package com.basejava.storage;

import static org.junit.jupiter.api.Assertions.*;

class ListStorageTest extends AbstractStorageTest {
    protected ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    protected void assertSize(int size) {
        assertEquals(size, storage.getAll().length);
    }
}