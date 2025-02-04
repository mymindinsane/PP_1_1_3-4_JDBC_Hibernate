package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final Session currentSession = Util.openCurrentSession();
    private Transaction transaction = currentSession.getTransaction();


    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try {
            if (!transaction.isActive()) {
                transaction = currentSession.beginTransaction();
            }
            Query<User> query = currentSession.createNativeQuery("CREATE TABLE IF NOT EXISTS sakila.users_table"
                    + "(Id  INT NOT NULL PRIMARY KEY AUTO_INCREMENT ," + "Age TINYINT NOT NULL ,"
                    + "FirstName VARCHAR(20) NOT NULL ," + "LastName VARCHAR(20) NOT NULL);", User.class);
            query.executeUpdate();
            transaction.commit();
            System.out.println("Table created successfully");
        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("Error when creating table");
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            if (!transaction.isActive()) {
                transaction = currentSession.beginTransaction();
            }
            Query<User> query = currentSession.createNativeQuery("DROP TABLE IF EXISTS sakila.users_table",
                    User.class);
            query.executeUpdate();
            transaction.commit();
            System.out.println("Table dropped successfully");
        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("Error when dropping table");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            if (!transaction.isActive()) {
                transaction = currentSession.beginTransaction();
            }
            currentSession.persist(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("Error when saving user");
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            if (!transaction.isActive()) {
                transaction = currentSession.beginTransaction();
            }
            User user = currentSession.find(User.class, id);
            if (user != null) {
                currentSession.remove(user);
            }
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("Error when removing user by id");
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return currentSession.createNativeQuery("SELECT * FROM sakila.users_table", User.class).list();
        } catch (HibernateException e) {
            System.out.println("Error when getting the list of all users");
        }
        return List.of();
    }

    @Override
    public void cleanUsersTable() {

        try {
            if (!transaction.isActive()) {
                transaction = currentSession.beginTransaction();
            }
            Query<User> query = currentSession.createNativeQuery("DELETE FROM sakila.users_table", User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("Error when clearing the table");
        }
    }
}



