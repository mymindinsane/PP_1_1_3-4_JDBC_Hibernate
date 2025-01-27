package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;



import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS sakila.users_table" +
                    "(Id  INT NOT NULL PRIMARY KEY AUTO_INCREMENT ," +
                    "Age TINYINT NOT NULL ," +
                    "FirstName VARCHAR(20) NOT NULL ," +
                    "LastName VARCHAR(20) NOT NULL);").addEntity(User.class).executeUpdate();
            transaction.commit();
            System.out.println("Table created successfully");
        } catch (HibernateException e){

        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS sakila.users_table")
                    .addEntity(User.class).executeUpdate();
            transaction.commit();
            System.out.println("Table dropped successfully");
        } catch (HibernateException e){

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(new User(name,lastName,age));
            transaction.commit();
        } catch (HibernateException e){

        }
    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }
}
