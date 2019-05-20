package com.basejava.storage;

import com.basejava.exceptions.ExistedStorageException;
import com.basejava.exceptions.NotExistedStorageException;
import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;
import com.basejava.sql.ConnectionFactory;
import com.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void update(Resume resume) {
        String sql = "UPDATE resumes SET full_name = ? WHERE uuid = ?";
        List<String> params = Arrays.asList(resume.getFullName(), resume.getUuid());

        try {
            SqlHelper.update(connectionFactory, sql, params);
        } catch (SQLException e) {
            throw new NotExistedStorageException(resume.getUuid());
        }
    }

    @Override
    public void clear() {
        try {
            SqlHelper.deleteAll(connectionFactory, "resumes");
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void save(Resume resume) {
        String sql = "INSERT INTO resumes (uuid, full_name) VALUES (?, ?)";
        List<String> params = Arrays.asList(resume.getUuid(), resume.getFullName());

        try {
            SqlHelper.insert(connectionFactory, sql, params);
        } catch (SQLException e) {
            throw new ExistedStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        String sql = "SELECT * FROM resumes WHERE uuid = ?";
        List<String> params = Collections.singletonList(uuid);

        try (ResultSet rs = SqlHelper.select(connectionFactory, sql, params)) {
            if (!rs.next()) {
                throw new NotExistedStorageException(uuid);
            }

            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void delete(String uuid) {
        String sql = "DELETE FROM resumes WHERE uuid = ?";
        List<String> params = Collections.singletonList(uuid);

        try {
            SqlHelper.delete(connectionFactory, sql, params);
        } catch (SQLException e) {
            throw new NotExistedStorageException(uuid);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        String sql = "SELECT * FROM resumes ORDER BY uuid";

        try (ResultSet rs = SqlHelper.select(connectionFactory, sql, new ArrayList<>())) {
            List<Resume> resumes = new ArrayList<>();

            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }

            return resumes;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
