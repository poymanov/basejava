package com.basejava.storage;

import static org.junit.jupiter.api.Assertions.*;

class MapStorageTest extends AbstractStorageTest {
    protected MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    protected void assertSize(int size) {
        assertEquals(size, storage.getAllSorted().size());
    }
}