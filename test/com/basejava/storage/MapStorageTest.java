package com.basejava.storage;

import static org.junit.Assert.assertEquals;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    protected void assertSize(int size) {
        assertEquals(size, storage.getAllSorted().size());
    }
}