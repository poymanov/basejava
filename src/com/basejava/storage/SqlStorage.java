package com.basejava.storage;

import com.basejava.exceptions.ExistedStorageException;
import com.basejava.exceptions.NotExistedStorageException;
import com.basejava.model.Resume;
import com.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String sql = "INSERT INTO resumes (uuid, full_name) VALUES (?, ?)";

        sqlHelper.setParams(new HashMap<String, String>() {{
            put("uuid", resume.getUuid());
        }});

        sqlHelper.execute(sql, (ps) -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();

            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        String sql = "SELECT * FROM resumes WHERE uuid = ?";
        return sqlHelper.execute(sql, (ps) -> {
            ps.setString(1, uuid);
            ps.executeQuery();

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new NotExistedStorageException(uuid);
            }

            return new Resume(uuid, rs.getString("full_name"));
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
        String sql = "SELECT * FROM resumes ORDER BY uuid";
        return sqlHelper.execute(sql, (ps) -> {
            ps.executeQuery();

            ResultSet rs = ps.executeQuery();

            List<Resume> resumes = new ArrayList<>();

            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }

            return resumes;
        });
    }
}
