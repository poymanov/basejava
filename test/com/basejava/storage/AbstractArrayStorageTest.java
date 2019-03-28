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

    private static final Resume resume1 = new Resume("uuid1");
    private static final Resume resume2 = new Resume("uuid2");
    private static final Resume resume3 = new Resume("uuid3");

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    void updateNotExisted() {
        assertThrows(NotExistedStorageException.class, () -> {
            storage.update(new Resume("dummy"));
        });
    }

    @Test
    void update() {
        Resume uuid4 = new Resume("uuid1");
        storage.update(uuid4);
        assertSame(uuid4, storage.get("uuid1"));
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void saveExistedResume() {
        assertThrows(ExistedStorageException.class, () -> {
            storage.save(new Resume("uuid1"));
        });
    }

    @Test
    void saveOverflowError() {
        storage.clear();

        for (int i = 0; i < 10000; i++) {
            storage.save(new Resume());
        }

        assertThrows(StorageException.class, () -> {
            storage.save(new Resume());
        });
    }

    @Test
    void save() {
        Resume resume4 = new Resume("uuid4");
        storage.save(resume4);
        assertEquals(4, storage.size());
        assertSame(resume4, storage.get("uuid4"));
    }

    @Test
    void getNotExisted() {
        assertThrows(NotExistedStorageException.class, () -> {
            storage.get("dummy");
        });
    }

    @Test
    void get() {
        assertEquals(resume1, storage.get("uuid1"));
    }

    @Test
    void deleteNotExisted() {
        assertThrows(NotExistedStorageException.class, () -> {
            storage.delete("dummy");
        });
    }

    @Test
    void delete() {
        storage.delete(resume1.getUuid());
        assertEquals(2, storage.size());

        assertThrows(NotExistedStorageException.class, () -> {
            storage.get(resume1.getUuid());
        });
    }

    @Test
    void getAll() {
        assertArrayEquals(new Resume[]{resume1, resume2, resume3}, storage.getAll());
    }

    @Test
    void size() {
        assertEquals(3, storage.size());
    }
}