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

import static junit.framework.TestCase.*;

public abstract class AbstractStorageTest {
    protected static final String STORAGE_DIR = Config.get().getStorageDir().getAbsolutePath();
    protected final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_DUMMY = "dummy";

    private static Resume RESUME_1;
    private static Resume RESUME_2;
    private static Resume RESUME_3;
    private static Resume RESUME_4;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;

        RESUME_1 = new Resume(UUID_1, "Test Name");
        RESUME_2 = new Resume(UUID_2, "Test Name 2");
        RESUME_3 = new Resume(UUID_3, "Test Name 3");

        RESUME_3.addContact(ContactType.PHONE, "+7(111) 111-1111");
        RESUME_3.addContact(ContactType.EMAIL, "test@test.ru");
        RESUME_3.addSection(SectionType.OBJECTIVE, new TextSection("Objective"));
        RESUME_3.addSection(SectionType.PERSONAL, new TextSection("Personal"));

        ArrayList<String> achievementData = new ArrayList<>();
        achievementData.add("Achievement 1");
        achievementData.add("Achievement 2");

        RESUME_3.addSection(SectionType.ACHIEVEMENT, new ListSection(achievementData));

        ArrayList<String> qualificationsData = new ArrayList<>();
        qualificationsData.add("Qualification 1");
        qualificationsData.add("Qualification 2");

        RESUME_3.addSection(SectionType.QUALIFICATIONS, new ListSection(qualificationsData));

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
        Resume resume = RESUME_3;

        resume.addContact(ContactType.PHONE, "+7(222) 222-2222");
        resume.addContact(ContactType.EMAIL, "test2@test.ru");
        storage.update(resume);

        resume = storage.get(UUID_3);

        assertEquals("+7(222) 222-2222", resume.getContacts().get(ContactType.PHONE).getTitle());
        assertEquals("test2@test.ru", resume.getContacts().get(ContactType.EMAIL).getTitle());

        resume.getContacts().remove(ContactType.PHONE);
        storage.update(resume);

        resume = storage.get(UUID_3);

        assertEquals(1, resume.getContacts().size());
        assertNull(resume.getContacts().get(ContactType.PHONE));

        resume.getContacts().clear();
        storage.update(resume);

        resume = storage.get(UUID_3);

        assertEquals(0, resume.getContacts().size());
        assertNull(resume.getContacts().get(ContactType.EMAIL));
    }

    @Test
    public void updateSections() {
        Resume resume = RESUME_3;

        resume.addSection(SectionType.OBJECTIVE, new TextSection("Objective 2"));

        ArrayList<String> achievementData = new ArrayList<>();
        achievementData.add("Achievement 11");
        achievementData.add("Achievement 22");

        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(achievementData));

        storage.update(resume);

        resume = storage.get(UUID_3);

        assertEquals("Objective 2", ((TextSection) resume.getSections().get(SectionType.OBJECTIVE)).getTitle());
        assertEquals("Achievement 11", ((ListSection) resume.getSections().get(SectionType.ACHIEVEMENT)).getItems().get(0));
        assertEquals("Achievement 22", ((ListSection) resume.getSections().get(SectionType.ACHIEVEMENT)).getItems().get(1));

        resume.getSections().remove(SectionType.ACHIEVEMENT);
        storage.update(resume);

        resume = storage.get(UUID_3);

        assertEquals(3, resume.getSections().size());
        assertNull(resume.getSections().get(SectionType.ACHIEVEMENT));

        resume.getSections().clear();
        storage.update(resume);

        resume = storage.get(UUID_3);

        assertEquals(0, resume.getSections().size());
        assertNull(resume.getSections().get(SectionType.OBJECTIVE));
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
