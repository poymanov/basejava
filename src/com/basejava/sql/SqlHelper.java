package com.basejava.sql;

import com.basejava.exceptions.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <V> V execute(SqlExecute<V> sqlCommands){
        try (Connection connection = connectionFactory.getConnection()) {
            return sqlCommands.execute(connection);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
