package com.basejava.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface SqlSelect <V> {
    V select(Connection connection) throws SQLException;
}
