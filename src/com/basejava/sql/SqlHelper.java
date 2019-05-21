package com.basejava.sql;

import com.basejava.exceptions.ExistedStorageException;
import com.basejava.exceptions.NotExistedStorageException;
import com.basejava.exceptions.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public void execute(SqlExecute sqlCommands){
        try (Connection connection = connectionFactory.getConnection()) {
            sqlCommands.execute(connection);
        } catch (NotExistedStorageException e) {
            throw new NotExistedStorageException(e.getMessage());
        } catch (ExistedStorageException e) {
            throw new ExistedStorageException(e.getMessage());
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <V> V select(SqlSelect<V> sqlSelect) {
        try (Connection connection = connectionFactory.getConnection()) {
            return sqlSelect.select(connection);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
