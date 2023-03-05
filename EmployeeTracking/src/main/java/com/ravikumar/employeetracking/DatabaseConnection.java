package com.ravikumar.employeetracking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getDatabase() throws SQLException, ClassNotFoundException {
        String dbDriver = "com.mysql.jdbc.Driver";
        String dbURL = "jdbc:mysql://localhost:3306/";
        String dbName = "employeetracking";
        String dbUsername = "root";
        String dbPassword = "";
        Class.forName(dbDriver);
        Connection con = DriverManager.getConnection(dbURL + dbName,dbUsername,dbPassword);
        return con;
    }
}
