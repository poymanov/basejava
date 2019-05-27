package com.basejava.storage;

import com.basejava.exceptions.NotExistedStorageException;
import com.basejava.model.*;
import com.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            updateResume(conn, resume);
            removeContacts(conn, resume);
            addContacts(conn, resume);
            removeSections(conn, resume);
            addSections(conn, resume);
            return null;
        }, null);
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM resumes";
        sqlHelper.execute(sql, (ps) -> {
            ps.execute();
            return null;
        }, null);
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            insertResume(conn, resume);
            addContacts(conn, resume);
            addSections(conn, resume);
            return null;
        }, resume.getUuid());
    }

    @Override
    public Resume get(String uuid) {
        String sql = "SELECT * FROM resumes r LEFT JOIN contacts c ON r.uuid = c.resume_uuid WHERE uuid = ?";
        return sqlHelper.execute(sql, (ps) -> {
            ps.setString(1, uuid);

            List<Resume> resumes = getResumes(ps.executeQuery());

            if (resumes.size() > 0) {
                return resumes.get(0);
            } else {
                throw new NotExistedStorageException(uuid);
            }
        }, null);
    }

    @Override
    public void delete(String uuid) {
        String sql = "DELETE FROM resumes WHERE uuid = ?";
        sqlHelper.execute(sql, (ps) -> {
            ps.setString(1, uuid);

            if (!ps.execute()) {
                throw new NotExistedStorageException(uuid);
            }

            return null;
        }, null);
    }

    @Override
    public List<Resume> getAllSorted() {
        String sql = "SELECT * FROM resumes ORDER BY uuid";
        return sqlHelper.execute(sql, (ps) -> getResumes(ps.executeQuery()), null);
    }

    private List<Resume> getResumes(ResultSet rs) throws SQLException {
        List<Resume> resumes = new ArrayList<>();

        while (rs.next()) {
            String uuid = rs.getString("uuid");
            String fullName = rs.getString("full_name");

            Resume resume = new Resume(uuid, fullName);

            Map<ContactType, Contact> contacts = sqlHelper.execute("SELECT type, value FROM contacts WHERE resume_uuid = ?", psc -> {
                Map<ContactType, Contact> contactsData = new EnumMap<>(ContactType.class);
                psc.setString(1, uuid);

                ResultSet rsc = psc.executeQuery();

                while (rsc.next()) {
                    ContactType type = ContactType.valueOf(rsc.getString("type"));
                    Contact contact = new Contact(rsc.getString("value"));
                    contactsData.put(type, contact);
                }

                return contactsData;
            }, null);

            Map<SectionType, AbstractSection> sections = sqlHelper.execute("SELECT type, value FROM sections WHERE resume_uuid = ?", pss -> {
                Map<SectionType, AbstractSection> sectionsData = new EnumMap<>(SectionType.class);
                pss.setString(1, uuid);

                ResultSet rss = pss.executeQuery();

                while (rss.next()) {
                    SectionType type = SectionType.valueOf(rss.getString("type"));

                    AbstractSection section;

                    String value = rss.getString("value");

                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            section = new TextSection(value);
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            section = new ListSection(new ArrayList<>(Arrays.asList(value.split("\n"))));
                            break;
                        default:
                            throw new SQLException("Undefined section type");
                    }

                    sectionsData.put(type, section);
                }

                return sectionsData;
            }, null);

            resume.setContacts(contacts);
            resume.setSections(sections);
            resumes.add(resume);
        }

        return resumes;
    }

    private void addContacts(Connection conn, Resume resume) throws SQLException {
        if (resume.getContacts().size() > 0) {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contacts (resume_uuid, type, value) VALUES (?, ?, ?)")) {
                for (Map.Entry<ContactType, Contact> entry : resume.getContacts().entrySet()) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, entry.getKey().name());
                    ps.setString(3, entry.getValue().getTitle());
                    ps.addBatch();
                }

                ps.executeBatch();
            }
        }
    }

    private void addSections(Connection conn, Resume resume) throws SQLException {
        if (resume.getSections().size() > 0) {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO sections (resume_uuid, type, value) VALUES (?, ?, ?)")) {
                for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, entry.getKey().name());

                    switch (entry.getKey()) {
                        case OBJECTIVE:
                        case PERSONAL:
                            ps.setString(3, ((TextSection) entry.getValue()).getTitle());
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            StringBuilder value = new StringBuilder();

                            for (String item: ((ListSection) entry.getValue()).getItems()) {
                                value.append(item).append("\n");
                            }

                            ps.setString(3, value.toString());

                            break;
                        default:
                            throw new SQLException("Undefined section type");
                    }

                    ps.addBatch();
                }

                ps.executeBatch();
            }
        }
    }

    private void removeContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contacts WHERE resume_uuid = ?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void removeSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM sections WHERE resume_uuid = ?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void insertResume(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resumes (uuid, full_name) VALUES (?, ?)")) {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());

            ps.execute();
        }
    }

    private void updateResume(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("UPDATE resumes SET full_name = ? WHERE uuid = ?")) {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());

            if (ps.executeUpdate() == 0) {
                throw new NotExistedStorageException(resume.getUuid());
            }
        }
    }
}
