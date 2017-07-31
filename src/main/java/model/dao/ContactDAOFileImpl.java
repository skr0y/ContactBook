package model.dao;

import model.entities.Contact;
import model.entities.Group;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class ContactDAOFileImpl implements ContactDAO {
    private final String DEFAULT_FILENAME = "contacts.db";

    private String fileName = DEFAULT_FILENAME;

    private boolean save(Set<Contact> contacts) {
        File dbFile = new File(fileName);
        try {
            dbFile.createNewFile();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dbFile, false))) {
                for (Contact contact : contacts) {
                    oos.writeObject(contact);
                }
            } catch (Exception e) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean add(Contact contact) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName, true))) {
            oos.writeObject(contact);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean update(Contact contact) {
        Set<Contact> contacts = getAll();
        contacts.stream().filter(x -> x.getId() == contact.getId()).findFirst().ifPresent(contacts::remove);
        contacts.add(contact);
        return save(contacts);
    }

    public boolean delete(Contact contact) {
        Set<Contact> contacts = getAll();
        contacts.stream().filter(x -> x.getId() == contact.getId()).findFirst().ifPresent(contacts::remove);
        return save(contacts);
    }

    public boolean deleteGroup(Group group) {
        Set<Contact> contacts = getAll();
        for (Contact contact : contacts) {
            if (contact.getGroupId() == group.getId()) {
                contact.removeGroup();
            }
        }
        return true;
    }

    public Contact get(int id) {
        Set<Contact> contacts = getAll();
        return contacts.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    public Set<Contact> getAll() {
        Set<Contact> contacts = new HashSet<>();
        int lastId = 0;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Contact input;
            while (true) {
                input = (Contact)ois.readObject();
                if (input == null) break;
                contacts.add(input);
                if (input.getId() > lastId) {
                    lastId = input.getId();
                }
            }
        } catch (Exception e) {
            //
        }
        Contact.lastId = lastId;
        return contacts;
    }
}
