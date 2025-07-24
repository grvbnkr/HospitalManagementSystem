
package com.hms.util;
import java.sql.*;


public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String USER = "root"; 
    private static final String PASSWORD = "abc123"; 

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}