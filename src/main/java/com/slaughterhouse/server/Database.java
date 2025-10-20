package com.slaughterhouse.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private final Connection conn;

    public Database(String url, String user, String password) throws SQLException {
        this.conn = DriverManager.getConnection(url, user, password);
    }

    public Connection getConnection() {
        return conn;
    }

    public void close() throws SQLException {
        conn.close();
    }
}
