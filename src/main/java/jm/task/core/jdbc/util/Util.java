package jm.task.core.jdbc.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private final static String URL = "jdbc:mysql://localhost:3306/sakila";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";
    private static Connection connection;


    public static Connection getConnection() {
        connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Сonnection to the database was successful");
        } catch (SQLException e) {
            System.out.println("Error when trying to connect to the database");
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()){
                connection.close();
                System.out.println("The connection was successfully closed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
