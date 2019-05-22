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
        String sql = "UPDATE resumes SET full_name = ? WHERE uuid = ?";
        sqlHelper.execute(sql, (ps) -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());

            if (ps.executeUpdate() == 0) {
                throw new NotExistedStorageException(resume.getUuid());
            }

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

        sqlHelper.transactionalExecute((conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resumes (uuid, full_name) VALUES (?, ?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }

            if (resume.getContacts().size() > 0) {
                try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contacts (resume_uuid, type, value) VALUES (?, ?, ?)")) {
                    for (Map.Entry<ContactType, Contact> entry: resume.getContacts().entrySet()) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, entry.getKey().name());
                        ps.setString(3, entry.getValue().getTitle());
                        ps.addBatch();
                    }

                    ps.executeBatch();
                }
            }

            return null;
        }));
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
        return sqlHelper.execute(sql, (ps) -> getResumes(ps.executeQuery()));
    }

    private List<Resume> getResumes(ResultSet rs) throws SQLException {
        Map<String, Resume> resumesData = new TreeMap<>();

        while (rs.next()) {
            String uuid = rs.getString("uuid");
            String fullName = rs.getString("full_name");
            String type = rs.getString("type");
            String value = rs.getString("value");

            Resume resume;

            if (resumesData.containsKey(uuid)) {
                resume = resumesData.get(uuid);
            } else {
                resume = new Resume(uuid, fullName);
                resumesData.put(uuid, resume);
            }

            if (type == null || value == null) {
                continue;
            }

            ContactType contactType = ContactType.valueOf(type);
            resume.addContact(contactType, value);
        }

        return Arrays.asList(resumesData.values().toArray(new Resume[0]));
    }
}
