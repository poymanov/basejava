package com.basejava.storage;

import com.basejava.exceptions.ExistedStorageException;
import com.basejava.exceptions.NotExistedStorageException;
import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractArrayStorageTest {
    private final AbstractArrayStorage storage;

    private static final Resume RESUME_1 = new Resume("uuid1");
    private static final Resume RESUME_2 = new Resume("uuid2");
    private static final Resume RESUME_3 = new Resume("uuid3");

    protected AbstractArrayStorageTest(AbstractArrayStorage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
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

        for (int i = 0; i < AbstractArrayStorage.MAX_SIZE; i++) {
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
        assertEquals(RESUME_1, storage.get("uuid1"));
    }

    @Test
    void deleteNotExisted() {
        assertThrows(NotExistedStorageException.class, () -> {
            storage.delete("dummy");
        });
    }

    @Test
    void delete() {
        storage.delete(RESUME_1.getUuid());
        assertEquals(2, storage.size());

        assertThrows(NotExistedStorageException.class, () -> {
            storage.get(RESUME_1.getUuid());
        });
    }

    @Test
    void getAll() {
        assertArrayEquals(new Resume[]{RESUME_1, RESUME_2, RESUME_3}, storage.getAll());
    }

    @Test
    void size() {
        assertEquals(3, storage.size());
    }
}