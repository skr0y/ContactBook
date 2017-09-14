package dao;

import entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
    @Autowired
    private SessionFactory sessionFactory;

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

    public synchronized List<User> getAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Users");
        List<User> users = query.list();

        session.getTransaction().commit();
        session.close();
        return users;
    }
}
