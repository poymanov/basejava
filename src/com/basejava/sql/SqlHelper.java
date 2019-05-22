package com.basejava.sql;

import com.basejava.exceptions.ExistedStorageException;
import com.basejava.exceptions.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;
    private Map<String, String> params;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <V> V execute(String sql, SqlExecute<V> sqlCommands){
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            return sqlCommands.execute(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistedStorageException(params.get("uuid"));
            } else {
                throw new StorageException(e);
            }

        }
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
