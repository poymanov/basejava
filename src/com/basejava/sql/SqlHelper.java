package com.basejava.sql;

import com.basejava.exceptions.ExistedStorageException;
import com.basejava.exceptions.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <V> V execute(String sql, SqlExecute<V> sqlCommands, String uuid) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            return sqlCommands.execute(ps);
        } catch (SQLException e) {
            throw getException(e, uuid);
        }
    }

    public <V> V transactionalExecute(SqlTransaction<V> sqlCommands, String uuid) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                V res = sqlCommands.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                throw getException(e, uuid);
            }
        } catch (SQLException e) {
            throw getException(e, uuid);
        }
    }

    private StorageException getException(SQLException e, String uuid) {
        if (e.getSQLState().equals("23505")) {
            return new ExistedStorageException(uuid);
        } else {
            return new StorageException(e);
        }
    }

}
