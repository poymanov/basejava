package com.basejava.storage;

import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected final AbstractArrayStorage storage;

    protected AbstractArrayStorageTest(AbstractArrayStorage storage) {
        super(storage);
        this.storage = storage;
    }

    @Test(expected = StorageException.class)
    public void saveOverflowError() {
        storage.clear();

        for (int i = 0; i < AbstractArrayStorage.MAX_SIZE; i++) {
            storage.save(new Resume("Test Name"));
        }

        storage.save(new Resume("Test Name"));
    }

    @Test
    public void size() {
        assertSize(3);
    }

    protected void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}