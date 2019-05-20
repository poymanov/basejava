package com.basejava.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SqlHelper {
    public static void update(ConnectionFactory connectionFactory, String sql, List<String> params) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(connectionFactory, sql)) {
            setParams(ps, params);

            if (ps.executeUpdate() == 0) {
                throw new SQLException("Not executed");
            }
        }
    }

    public static void deleteAll(ConnectionFactory connectionFactory, String table) throws SQLException {
        String sql = "DELETE FROM " + table;
        try (PreparedStatement ps = getConnection(connectionFactory).prepareStatement(sql)) {
            ps.execute();
        }
    }

    public static void insert(ConnectionFactory connectionFactory, String sql, List<String> params) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(connectionFactory, sql)) {
            setParams(ps, params);
            ps.execute();
        }
    }

    public static ResultSet select(ConnectionFactory connectionFactory, String sql, List<String> params) throws SQLException {
        PreparedStatement ps = getPrepareStatement(connectionFactory, sql);
        setParams(ps, params);
        return ps.executeQuery();
    }

    public static void delete(ConnectionFactory connectionFactory, String sql, List<String> params) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(connectionFactory, sql)) {
            setParams(ps, params);
            if (!ps.execute()) {
                throw new SQLException("Not executed");
            }
            ;
        }
    }

    private static Connection getConnection(ConnectionFactory connectionFactory) throws SQLException {
        return connectionFactory.getConnection();
    }

    private static PreparedStatement getPrepareStatement(ConnectionFactory connectionFactory, String sql) throws SQLException {
        return getConnection(connectionFactory).prepareStatement(sql);
    }

    private static void setParams(PreparedStatement ps, List<String> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            ps.setString(i + 1, params.get(i));
        }
    }
}
