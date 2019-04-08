package com.basejava.storage;

import com.basejava.exceptions.ExistedStorageException;
import com.basejava.exceptions.NotExistedStorageException;
import com.basejava.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertSame;


public abstract class AbstractStorageTest {
    protected final AbstractStorage storage;

    private static final Resume RESUME_1 = new Resume("uuid1", "Test Name");
    private static final Resume RESUME_2 = new Resume("uuid2", "Test Name 2");
    private static final Resume RESUME_3 = new Resume("uuid3", "Test Name 3");

    protected AbstractStorageTest(AbstractStorage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test(expected = NotExistedStorageException.class)
    public void updateNotExisted() {
        storage.update(new Resume("dummy"));
    }

    @Test
    public void update() {
        Resume uuid4 = new Resume("uuid1", "Test Name");
        storage.update(uuid4);
        assertSame(uuid4, storage.get("uuid1"));
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test(expected = ExistedStorageException.class)
    public void saveExistedResume() {
        storage.save(new Resume("uuid1", "Test Name"));
    }

    @Test
    public void save() {
        Resume resume4 = new Resume("uuid4", "Test Name");
        storage.save(resume4);
        assertSize(4);
        assertSame(resume4, storage.get("uuid4"));
    }

    @Test(expected = NotExistedStorageException.class)
    public void getNotExisted() {
        storage.get("dummy");
    }

    @Test
    public void get() {
        assertEquals(RESUME_1, storage.get("uuid1"));
    }

    @Test(expected = NotExistedStorageException.class)
    public void deleteNotExisted() {
        storage.delete("dummy");
    }

    @Test(expected = NotExistedStorageException.class)
    public void delete() {
        storage.delete(RESUME_1.getUuid());
        assertSize(2);
        storage.get(RESUME_1.getUuid());
    }

    @Test
    public void getAllSorted() {
        assertEquals(Arrays.asList(RESUME_1, RESUME_2, RESUME_3), storage.getAllSorted());
    }

    protected abstract void assertSize(int size);
}
