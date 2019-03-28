package com.basejava.storage;

import com.basejava.exceptions.ExistedStorageException;
import com.basejava.exceptions.NotExistedStorageException;
import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    private Resume uuid1 = new Resume("uuid1");
    private Resume uuid2 = new Resume("uuid2");
    private Resume uuid3 = new Resume("uuid3");

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(uuid1);
        storage.save(uuid2);
        storage.save(uuid3);
    }

    @Test
    public void updateNotExisted() {
        assertThrows(NotExistedStorageException.class, () -> {
            storage.update(new Resume("dummy"));
        });
    }

    @Test
    public void update() {
        Resume uuid4 = new Resume("uuid1");
        storage.update(uuid4);
        assertEquals(uuid4, storage.getAll()[0]);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void saveExistedResume() {
        assertThrows(ExistedStorageException.class, () -> {
            storage.save(new Resume("uuid1"));
        });
    }

    @Test
    public void saveOverflowError() {
        storage.save(new Resume("uuid4"));
        storage.save(new Resume("uuid5"));

        assertThrows(StorageException.class, () -> {
            storage.save(new Resume("uuid6"));
        });
    }

    @Test
    public void save() {
        storage.save(new Resume("uuid4"));
        assertEquals(4, storage.size());

        assertThrows(ExistedStorageException.class, () -> {
            storage.save(new Resume("uuid4"));
        });
    }

    @Test
    public void getNotExisted() {
        assertThrows(NotExistedStorageException.class, () -> {
            storage.get("dummy");
        });
    }

    @Test
    public void get() {
        assertEquals(uuid1, storage.get("uuid1"));
    }

    @Test
    public void deleteNotExisted() {
        assertThrows(NotExistedStorageException.class, () -> {
            storage.delete("dummy");
        });
    }

    @Test
    public void delete() {
        storage.delete(uuid1.getUuid());
        assertEquals(2, storage.size());
    }

    @Test
    public void getAll() {
        Resume[] allResumes = storage.getAll();
        assertEquals(uuid1, allResumes[0]);
        assertEquals(uuid2, allResumes[1]);
        assertEquals(uuid3, allResumes[2]);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }
}