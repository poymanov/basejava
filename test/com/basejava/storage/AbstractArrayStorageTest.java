package com.basejava.storage;

import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected AbstractArrayStorageTest(AbstractStorage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflowError() {
        try {
            storage.clear();

            for (int i = 0; i < AbstractArrayStorage.MAX_SIZE; i++) {
                storage.save(new Resume("Test Name"));
            }
        } catch (StorageException e) {
            Assert.fail();
        }

        storage.save(new Resume("Test Name"));
    }
}