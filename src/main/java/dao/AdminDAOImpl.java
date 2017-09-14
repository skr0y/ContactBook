package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("adminDAO")
public class AdminDAOImpl implements AdminDAO {
    private static AdminDAOImpl instance;

    static synchronized AdminDAOImpl getInstance() {
        if (instance == null) {
            instance = new AdminDAOImpl();
        }
        return instance;
    }

    @Autowired
    private SessionFactory sessionFactory;

    public synchronized int totalUsers() {
        String queryString = "SELECT \"UserCount\"()";
        int result = 0;

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        NativeQuery query = session.createNativeQuery(queryString);
        result = (int) query.getSingleResult();

        session.getTransaction().commit();
        session.close();
        return result;
    }

    public synchronized int contactsCountByUser(int userId) {
        String queryString = "SELECT \"ContactsCountByUser\"(:userid)";
        int result = 0;

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        NativeQuery query = session.createNativeQuery(queryString).setParameter("userid", userId);
        result = (int) query.getSingleResult();

        session.getTransaction().commit();
        session.close();
        return result;
    }

    public synchronized int groupsCountByUser(int userId) {
        String queryString = "SELECT \"GroupsCountByUser\"(:userid)";
        int result = 0;

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        NativeQuery query = session.createNativeQuery(queryString).setParameter("userid", userId);
        result = (int) query.getSingleResult();

        session.getTransaction().commit();
        session.close();
        return result;
    }

    public synchronized int averageContactsPerGroup() {
        String queryString = "SELECT \"AverageContactsPerGroup\"()";
        int result = 0;

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        NativeQuery query = session.createNativeQuery(queryString);
        result = (int) query.getSingleResult();

        session.getTransaction().commit();
        session.close();
        return result;
    }

    public synchronized int averageContactsPerUser() {
        String queryString = "SELECT \"AverageContactsPerUser\"()";
        int result = 0;

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        NativeQuery query = session.createNativeQuery(queryString);
        result = (int) query.getSingleResult();

        session.getTransaction().commit();
        session.close();
        return result;
    }

    public synchronized int inactiveUsersCount() {
        String queryString = "SELECT \"InactiveUsersCount\"()";
        int result = 0;

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        NativeQuery query = session.createNativeQuery(queryString);
        result = (int) query.getSingleResult();

        session.getTransaction().commit();
        session.close();
        return result;
    }
}
