package com.basejava.storage;

import com.basejava.Config;
import com.basejava.exceptions.ExistedStorageException;
import com.basejava.exceptions.NotExistedStorageException;
import com.basejava.model.*;
import com.sun.org.apache.regexp.internal.RE;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertSame;

public abstract class AbstractStorageTest {
    protected static final String STORAGE_DIR = Config.get().getStorageDir().getAbsolutePath();
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
//        RESUME_3.addSection(SectionType.OBJECTIVE, new TextSection("Objective"));
//        RESUME_3.addSection(SectionType.PERSONAL, new TextSection("Personal"));

//        ArrayList<String> achievementData = new ArrayList<>();
//        achievementData.add("Achievement 1");
//        achievementData.add("Achievement 2");
//
//        RESUME_3.addSection(SectionType.ACHIEVEMENT, new ListSection(achievementData));
//
//        ArrayList<String> qualificationsData = new ArrayList<>();
//        qualificationsData.add("Qualification 1");
//        qualificationsData.add("Qualification 2");
//
//        RESUME_3.addSection(SectionType.QUALIFICATIONS, new ListSection(qualificationsData));
//
//        Organization organization1 = new Organization("Title 1", "http://test.test", new ArrayList<Position>() {{
//            add(new Position("Title 1.1", LocalDate.of(2019, 10, 1), null));
//            add(new Position("Title 1.2", "Description 1.2",
//                    LocalDate.of(2019, 8, 1), LocalDate.of(2019, 9, 1)));
//        }});
//
//        ArrayList<Organization> experienceList = new ArrayList<>();
//        experienceList.add(organization1);
//
//        RESUME_3.addSection(SectionType.EXPERIENCE, new OrganizationSection(experienceList));
//
//        Organization organization2 = new Organization("Title 2", new ArrayList<Position>() {{
//            add(new Position("Title 2.1", "Description 2.1",
//                    LocalDate.of(2019, 8, 1), null));
//            add(new Position("Title 2.2",
//                    LocalDate.of(2019, 6, 1), LocalDate.of(2019, 7, 1)));
//        }});
//
//        ArrayList<Organization> educationList = new ArrayList<>();
//        educationList.add(organization2);
//
//        RESUME_3.addSection(SectionType.EDUCATION, new OrganizationSection(educationList));

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
    public void updateContacts() {
        RESUME_3.addContact(ContactType.PHONE, "+7(222) 222-2222");
        RESUME_3.addContact(ContactType.EMAIL, "test2@test.ru");
        storage.update(RESUME_3);
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
