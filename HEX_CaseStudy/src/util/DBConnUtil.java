package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/Carrental";
    private static final String USER = "root";
    private static final String PASSWORD = "vishvas@12345";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to the database!\n", e);
        }
    }
}
