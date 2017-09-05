package model.dao;

import model.entities.Contact;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import view.ContextListener;

import java.util.HashSet;
import java.util.Set;

public class ContactDAODatabaseImpl implements ContactDAO {
    private static ContactDAODatabaseImpl instance;

    static synchronized ContactDAODatabaseImpl getInstance() {
        if (instance == null) {
            instance = new ContactDAODatabaseImpl();
        }
        return instance;
    }

    private SessionFactory sessionFactory = ContextListener.getSessionFactory();

    public synchronized boolean add(Contact contact) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(contact);

        session.getTransaction().commit();
        session.close();
        return true;
    }

    public synchronized boolean update(Contact contact) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(contact);

        session.getTransaction().commit();
        session.close();
        return true;
    }

    public synchronized boolean delete(Contact contact) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(contact);

        session.getTransaction().commit();
        session.close();
        return true;
    }

    public synchronized boolean deleteGroup(int groupId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        NativeQuery nativeQuery = session.createNativeQuery("SELECT \"RemoveGroupIdFromContacts\"(:id)");
        nativeQuery.setParameter("id", groupId);

        session.getTransaction().commit();
        session.close();
        return true;
    }

    public synchronized Contact get(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Contact contact = session.get(Contact.class, id);

        session.getTransaction().commit();
        session.close();
        return contact;
    }

    public synchronized Set<Contact> getAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Contacts");
        Set<Contact> contacts = new HashSet<>(query.list());

        session.getTransaction().commit();
        session.close();
        return contacts;
    }
}
