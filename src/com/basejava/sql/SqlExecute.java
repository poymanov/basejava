package com.basejava.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface SqlExecute <V> {
    V execute(Connection connection) throws SQLException;
}
