package com.basejava.storage;

import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected final AbstractArrayStorage storage;

    protected AbstractArrayStorageTest(AbstractArrayStorage storage) {
        super(storage);
        this.storage = storage;
    }

    @Test
    void saveOverflowError() {
        storage.clear();

        for (int i = 0; i < AbstractArrayStorage.MAX_SIZE; i++) {
            storage.save(new Resume("Test Name"));
        }

        assertThrows(StorageException.class, () -> {
            storage.save(new Resume("Test Name"));
        });
    }

    @Test
    void size() {
        assertSize(3);
    }

    protected void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}