package com.basejava.storage;

import static org.junit.jupiter.api.Assertions.*;

class MapHashStorageTest extends AbstractStorageTest {
    protected MapHashStorageTest() {
        super(new MapHashStorage());
    }

    @Override
    protected void assertSize(int size) {
        assertEquals(size, storage.getAllSorted().size());
    }
}