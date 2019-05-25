package com.basejava.sql;

import com.basejava.exceptions.ExistedStorageException;
import com.basejava.exceptions.StorageException;
import com.basejava.storage.Storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <V> V execute(String sql, SqlExecute<V> sqlCommands, Map params) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            return sqlCommands.execute(ps);
        } catch (SQLException e) {
            throw getException(e, params);
        }
    }

    public <V> V transactionalExecute(SqlTransaction<V> sqlCommands, Map params) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                V res = sqlCommands.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                throw getException(e, params);
            }
        } catch (SQLException e) {
            throw getException(e, params);
        }
    }

    private StorageException getException(SQLException e, Map<String, String> params) {
        if (e.getSQLState().equals("23505")) {
            return new ExistedStorageException(params.get("uuid"));
        } else {
            return new StorageException(e);
        }
    }

}
