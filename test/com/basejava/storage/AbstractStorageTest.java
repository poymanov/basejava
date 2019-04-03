package com.basejava.storage;

import com.basejava.exceptions.ExistedStorageException;
import com.basejava.exceptions.NotExistedStorageException;
import com.basejava.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {
    protected final AbstractStorage storage;

    private static final Resume RESUME_1 = new Resume("uuid1", "Test Name");
    private static final Resume RESUME_2 = new Resume("uuid2", "Test Name 2");
    private static final Resume RESUME_3 = new Resume("uuid3", "Test Name 3");

    protected AbstractStorageTest(AbstractStorage storage) {
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
        Resume uuid4 = new Resume("uuid1", "Test Name");
        storage.update(uuid4);
        assertSame(uuid4, storage.get("uuid1"));
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    void saveExistedResume() {
        assertThrows(ExistedStorageException.class, () -> {
            storage.save(new Resume("uuid1", "Test Name"));
        });
    }

    @Test
    void save() {
        Resume resume4 = new Resume("uuid4", "Test Name");
        storage.save(resume4);
        assertSize(4);
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
        assertSize(2);

        assertThrows(NotExistedStorageException.class, () -> {
            storage.get(RESUME_1.getUuid());
        });
    }

    @Test
    void getAllSorted() {
        assertEquals(Arrays.asList(RESUME_1, RESUME_2, RESUME_3), storage.getAllSorted());
    }

    protected abstract void assertSize(int size);
}
