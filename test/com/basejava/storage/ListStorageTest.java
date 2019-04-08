package com.basejava.storage;

import static org.junit.Assert.assertEquals;

public class ListStorageTest extends AbstractStorageTest {
    public ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    protected void assertSize(int size) {
        assertEquals(size, storage.getAllSorted().size());
    }
}