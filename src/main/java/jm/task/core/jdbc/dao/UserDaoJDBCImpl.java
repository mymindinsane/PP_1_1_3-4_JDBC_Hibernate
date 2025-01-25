package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE sakila.users_table" +
                    "(Id  INT NOT NULL PRIMARY KEY AUTO_INCREMENT ," +
                    "Age INT NOT NULL ," +
                    "FirstName VARCHAR(20) NOT NULL ," +
                    "LastName VARCHAR(20) NOT NULL);");
            System.out.println("Table created successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS sakila.users_table;");
            System.out.println("Table dropped successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO sakila.users_table (FirstName, LastName, Age) VALUES ((?), (?) , (?))";
        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
            System.out.println("User с именем — " +  name +  " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM sakila.users_table WHERE Id = (?)";
        try (final PreparedStatement statement =
                     connection.prepareStatement(sql)) {
            statement.setInt(1, (int) id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM sakila.users_table");
            while (resultSet.next()) {
                allUsers.add(new User(resultSet.getString(3),
                        resultSet.getString(4),
                        (byte) resultSet.getInt(2)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM sakila.users_table");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
