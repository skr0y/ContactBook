package dao;

import entities.Group;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("groupDAO")
public class GroupDAOImpl implements GroupDAO {
    private static GroupDAOImpl instance;

    static synchronized GroupDAOImpl getInstance() {
        if (instance == null) {
            instance = new GroupDAOImpl();
        }
        return instance;
    }

    @Autowired
    private SessionFactory sessionFactory;

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

    public synchronized List<Group> getAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Groups");
        List<Group> groups = query.list();

        session.getTransaction().commit();
        session.close();
        return groups;
    }
}
