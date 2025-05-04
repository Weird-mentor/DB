package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class TestDBConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/Carrental";
    private static final String USER = "root";
    private static final String PASSWORD = "vishvas@12345";
    
    public static void main(String[] args) {
        try {
        	Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (connection != null) {
                System.out.println("Database Connection Successful!");
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed !!!\nError: " + e.getMessage());
        }
    }
    
}
