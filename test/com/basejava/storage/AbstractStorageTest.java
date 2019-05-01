package com.basejava.storage;

import com.basejava.exceptions.ExistedStorageException;
import com.basejava.exceptions.NotExistedStorageException;
import com.basejava.model.Contact;
import com.basejava.model.ContactType;
import com.basejava.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertSame;

public abstract class AbstractStorageTest {
    protected static final String STORAGE_DIR = "./storage";
    protected final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_DUMMY = "dummy";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, "Test Name");
        RESUME_2 = new Resume(UUID_2, "Test Name 2");
        RESUME_3 = new Resume(UUID_3, "Test Name 3");

        RESUME_3.addContact(ContactType.PHONE, "+7(111) 111-1111");
        RESUME_3.addContact(ContactType.EMAIL, "test@test.ru");

        RESUME_4 = new Resume(UUID_4, "Test Name 4");
    }

    protected AbstractStorageTest(Storage storage) {
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
        storage.update(new Resume(UUID_DUMMY));
    }

    @Test
    public void update() {
        Resume uuid4 = new Resume(UUID_1, "Test Name");
        storage.update(uuid4);
        assertEquals(uuid4, storage.get(UUID_1));
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test(expected = ExistedStorageException.class)
    public void saveExistedResume() {
        storage.save(RESUME_1);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertEquals(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = NotExistedStorageException.class)
    public void getNotExisted() {
        storage.get(UUID_DUMMY);
    }

    @Test
    public void get() {
        assertEquals(RESUME_1, storage.get(UUID_1));
        assertEquals(RESUME_2, storage.get(UUID_2));
        assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test(expected = NotExistedStorageException.class)
    public void deleteNotExisted() {
        storage.delete(UUID_DUMMY);
    }

    @Test(expected = NotExistedStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test
    public void getAllSorted() {
        ArrayList<Resume> resumes = new ArrayList<>();
        resumes.add(RESUME_1);
        resumes.add(RESUME_2);
        resumes.add(RESUME_3);
        resumes.sort(Resume::compareTo);

        assertEquals(resumes, storage.getAllSorted());
    }

    @Test
    public void size() {
        assertSize(3);
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.getAllSorted().size());
    }
}
