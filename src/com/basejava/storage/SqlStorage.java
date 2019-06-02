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
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume;

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resumes WHERE uuid = ?")) {
                ps.setString(1, uuid);

                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    throw new NotExistedStorageException(uuid);
                }

                resume = getResumeFromSet(rs);
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contacts WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                   addContactFromSet(resume, rs);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM sections WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    addSectionFromSet(resume, rs);
                }
            }

            return resume;
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
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resumes ORDER BY uuid")) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Resume resume = getResumeFromSet(rs);
                    resumes.put(resume.getUuid(), resume);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contacts")) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    String resumeUuid = rs.getString("resume_uuid");

                    if (!resumes.containsKey(resumeUuid)) {
                        continue;
                    }

                    addContactFromSet(resumes.get(resumeUuid), rs);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM sections")) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    String resumeUuid = rs.getString("resume_uuid");

                    if (!resumes.containsKey(resumeUuid)) {
                        continue;
                    }
                    addSectionFromSet(resumes.get(resumeUuid), rs);
                }
            }

            return new ArrayList<>(resumes.values());
        }, null);
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
                            ps.setString(3, String.join("\n", ((ListSection) entry.getValue()).getItems()));
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

    private AbstractSection getSectionByType(SectionType type, String value) throws SQLException {
        switch (type) {
            case OBJECTIVE:
            case PERSONAL:
                return new TextSection(value);
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(new ArrayList<>(Arrays.asList(value.split("\n"))));
            default:
                throw new SQLException("Undefined section type");
        }
    }

    private void addSectionFromSet(Resume resume, ResultSet rs) throws SQLException {
        SectionType type = SectionType.valueOf(rs.getString("type"));
        String value = rs.getString("value");
        resume.addSection(type, getSectionByType(type, value));
    }

    private void addContactFromSet(Resume resume, ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        String value = rs.getString("value");
        resume.addContact(ContactType.valueOf(type), value);
    }

    private Resume getResumeFromSet(ResultSet rs) throws SQLException {
        return new Resume(rs.getString("uuid"), rs.getString("full_name"));
    }
}
