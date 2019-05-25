package com.basejava.storage;

import com.basejava.exceptions.ExistedStorageException;
import com.basejava.exceptions.NotExistedStorageException;
import com.basejava.model.Contact;
import com.basejava.model.ContactType;
import com.basejava.model.Resume;
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
            addResume(conn, resume, false);
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
            addResume(conn, resume, true);
            return null;
        }, new HashMap<String, String>() {{
            put("uuid", resume.getUuid());
        }});
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
        String sql = "SELECT * FROM resumes r LEFT JOIN contacts c ON r.uuid = c.resume_uuid ORDER BY uuid";
        return sqlHelper.execute(sql, (ps) -> getResumes(ps.executeQuery()), null);
    }

    private List<Resume> getResumes(ResultSet rs) throws SQLException {
        List<Resume> resumes = new ArrayList<>();

        while (rs.next()) {
            String uuid = rs.getString("uuid");
            String fullName = rs.getString("full_name");
            String type = rs.getString("type");
            String value = rs.getString("value");

            Optional resumeOptional = resumes.stream().filter(o -> o.getUuid().equals(uuid)).findFirst();

            Resume resume;

            if (resumeOptional.isPresent()) {
                resume = (Resume) resumeOptional.get();
            } else {
                resume = new Resume(uuid, fullName);
                resumes.add(resume);
            }

            if (type == null || value == null) {
                continue;
            }

            ContactType contactType = ContactType.valueOf(type);
            resume.addContact(contactType, value);
        }

        return resumes;
    }

    private void addResume(Connection conn, Resume resume, boolean isNew) throws SQLException {
        String contactsSql;

        if (isNew) {
            insertResume(conn, resume);
            contactsSql = "INSERT INTO contacts (resume_uuid, type, value) VALUES (?, ?, ?)";
        } else {
            updateResume(conn, resume);
            contactsSql = "UPDATE contacts SET type = ?, value = ? WHERE resume_uuid = ?";
        }

        if (resume.getContacts().size() > 0) {
            try (PreparedStatement ps = conn.prepareStatement(contactsSql)) {
                for (Map.Entry<ContactType, Contact> entry : resume.getContacts().entrySet()) {
                    ps.setString(1, isNew ? resume.getUuid() : entry.getKey().name());
                    ps.setString(2, isNew ? entry.getKey().name() : entry.getValue().getTitle());
                    ps.setString(3, isNew ? entry.getValue().getTitle() : resume.getUuid());
                    ps.addBatch();
                }

                ps.executeBatch();
            }
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
