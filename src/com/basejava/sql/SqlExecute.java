package com.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlExecute <V> {
    V execute(PreparedStatement ps) throws SQLException;
}
