package dao;

import entities.Contact;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("contactDAO")
public class ContactDAOImpl implements ContactDAO {
    private static ContactDAOImpl instance;

    static synchronized ContactDAOImpl getInstance() {
        if (instance == null) {
            instance = new ContactDAOImpl();
        }
        return instance;
    }

    @Autowired
    private SessionFactory sessionFactory;

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

    public synchronized List<Contact> getAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Contacts");
        List<Contact> contacts = query.list();

        session.getTransaction().commit();
        session.close();
        return contacts;
    }
}
