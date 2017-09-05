package model.dao;

import model.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import view.ContextListener;

import java.util.HashSet;
import java.util.Set;

public class UserDAODatabaseImpl implements UserDAO {
    private static UserDAODatabaseImpl instance;

    static synchronized UserDAODatabaseImpl getInstance() {
        if (instance == null) {
            instance = new UserDAODatabaseImpl();
        }
        return instance;
    }

    private SessionFactory sessionFactory = ContextListener.getSessionFactory();

    public synchronized boolean add(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(user);

        session.getTransaction().commit();
        session.close();
        return true;
    }

    public synchronized boolean update(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(user);

        session.getTransaction().commit();
        session.close();
        return true;
    }

    public synchronized boolean delete(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(user);

        session.getTransaction().commit();
        session.close();
        return true;
    }

    public synchronized User get(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = session.get(User.class, id);

        session.getTransaction().commit();
        session.close();
        return user;
    }

    public synchronized User get(String login) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Users WHERE Login = :login");
        query.setParameter("login", login);
        User user = (User) query.list().get(0);

        session.getTransaction().commit();
        session.close();
        return user;
    }

    public boolean checkCredentials(String login, String password) {
        User user = get(login);
        return user != null && user.getPassword().equals(password);
    }

    public synchronized Set<User> getAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Users");
        Set<User> users = new HashSet<>(query.list());

        session.getTransaction().commit();
        session.close();
        return users;
    }
}
