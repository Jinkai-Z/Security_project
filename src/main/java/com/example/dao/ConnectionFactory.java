package com.example.dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Database utils
 * @author Jinkai Zhang
 */
public class ConnectionFactory {
    private static DataSource dataSource;

    static {
        try {
            final InitialContext context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/db");
            context.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static <R> R execute(Query<Connection, R> query) throws SQLException {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                final R result = query.query(connection);
                connection.commit();
                return result;
            } catch (SQLException sqlException) {
                connection.rollback();
                throw sqlException;
            }
        }
    }

    public static <R> R execute(String sql, Query<PreparedStatement, R> query) throws SQLException {
        return execute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                return query.query(preparedStatement);
            }
        });
    }
    public static <R> R executeWithGeneratedKey(String sql, Query<PreparedStatement, R> query) throws SQLException {
        return execute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                return query.query(preparedStatement);
            }
        });
    }

    public static interface Query<T, R> {
        public R query(T connection) throws SQLException;
    }
}
