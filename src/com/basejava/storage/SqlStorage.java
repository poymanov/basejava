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
        });
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM resumes";
        sqlHelper.execute(sql, (ps) -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.setParams(new HashMap<String, String>() {{
            put("uuid", resume.getUuid());
        }});

        sqlHelper.transactionalExecute(conn -> {
            addResume(conn, resume, true);
            return null;
        });
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
        });
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
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        String sql = "SELECT * FROM resumes r LEFT JOIN contacts c ON r.uuid = c.resume_uuid ORDER BY uuid";
        return sqlHelper.execute(sql, (ps) -> {
            List<Resume> resumes = getResumes(ps.executeQuery());
            resumes.sort(Resume::compareTo);
            return resumes;
        });
    }

    private List<Resume> getResumes(ResultSet rs) throws SQLException {
        Map<String, Resume> resumesData = new HashMap<>();

        while (rs.next()) {
            String uuid = rs.getString("uuid");
            String fullName = rs.getString("full_name");
            String type = rs.getString("type");
            String value = rs.getString("value");

            resumesData.computeIfAbsent(uuid, key -> new Resume(uuid, fullName));

            if (type == null || value == null) {
                continue;
            }

            Resume resume = resumesData.get(uuid);

            ContactType contactType = ContactType.valueOf(type);
            resume.addContact(contactType, value);
        }

        return new ArrayList<>(resumesData.values());
    }

    private void addResume(Connection conn, Resume resume, boolean isNew) throws SQLException {
        String resumeSql = isNew ?
                "INSERT INTO resumes (uuid, full_name) VALUES (?, ?)" :
                "UPDATE resumes SET full_name = ? WHERE uuid = ?";

        String contactsSql = isNew ?
                "INSERT INTO contacts (resume_uuid, type, value) VALUES (?, ?, ?)" :
                "UPDATE contacts SET type = ?, value = ? WHERE resume_uuid = ?";

        try (PreparedStatement ps = conn.prepareStatement(resumeSql)) {
            ps.setString(1, isNew ? resume.getUuid() : resume.getFullName());
            ps.setString(2, isNew ? resume.getFullName() : resume.getUuid());

            if (isNew) {
                ps.execute();
            } else {
                if (ps.executeUpdate() == 0) {
                    throw new NotExistedStorageException(resume.getUuid());
                }
            }
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
}
