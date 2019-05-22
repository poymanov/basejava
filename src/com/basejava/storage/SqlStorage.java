package com.basejava.storage;

import com.basejava.exceptions.ExistedStorageException;
import com.basejava.exceptions.NotExistedStorageException;
import com.basejava.model.Resume;
import com.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.execute((conn) -> {
            PreparedStatement ps = conn.prepareStatement("UPDATE resumes SET full_name = ? WHERE uuid = ?");
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
        sqlHelper.execute((conn) -> {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM resumes");
            ps.execute();

            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.execute((conn) -> {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO resumes (uuid, full_name) VALUES (?, ?)");
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());

            try {
                return ps.execute();
            } catch (SQLException e) {
                if (e.getSQLState().equals("23505")) {
                    throw new ExistedStorageException(resume.getUuid());
                } else {
                    throw new SQLException(e);
                }
            }
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute((conn) -> {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM resumes WHERE uuid = ?");
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
        sqlHelper.execute((conn) -> {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM resumes WHERE uuid = ?");
            ps.setString(1, uuid);

            if (!ps.execute()) {
                throw new NotExistedStorageException(uuid);
            }

            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute((conn) -> {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM resumes ORDER BY uuid");
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
