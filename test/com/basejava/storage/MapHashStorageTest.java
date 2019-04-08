package com.basejava.storage;

import static org.junit.Assert.assertEquals;

public class MapHashStorageTest extends AbstractStorageTest {
    public MapHashStorageTest() {
        super(new MapHashStorage());
    }

    @Override
    protected void assertSize(int size) {
        assertEquals(size, storage.getAllSorted().size());
    }
}