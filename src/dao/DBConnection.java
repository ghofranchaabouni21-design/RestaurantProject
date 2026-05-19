package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static Connection conn;

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/restaurant",
                        "root",
                        ""
                );
            }
        } catch (Exception e) {
            System.out.println("Connection error: " + e.getMessage());
        }
        return conn;
    }
}