package com.basejava.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface SqlTransaction <V> {
    V execute(Connection conn) throws SQLException;
}
