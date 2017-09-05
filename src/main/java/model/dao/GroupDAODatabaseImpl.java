package model.dao;

import model.entities.Group;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import view.ContextListener;

import java.util.HashSet;
import java.util.Set;

public class GroupDAODatabaseImpl implements GroupDAO {
    private static GroupDAODatabaseImpl instance;

    static synchronized GroupDAODatabaseImpl getInstance() {
        if (instance == null) {
            instance = new GroupDAODatabaseImpl();
        }
        return instance;
    }

    private SessionFactory sessionFactory = ContextListener.getSessionFactory();

    public synchronized boolean add(Group group) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(group);

        session.getTransaction().commit();
        session.close();
        return true;
    }

    public synchronized boolean update(Group group) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(group);

        session.getTransaction().commit();
        session.close();
        return true;
    }

    public synchronized boolean delete(Group group) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(group);

        session.getTransaction().commit();
        session.close();
        return true;
    }

    public synchronized Group get(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Group group = session.get(Group.class, id);

        session.getTransaction().commit();
        session.close();
        return group;
    }

    public synchronized Set<Group> getAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Groups");
        Set<Group> groups = new HashSet<>(query.list());

        session.getTransaction().commit();
        session.close();
        return groups;
    }
}
