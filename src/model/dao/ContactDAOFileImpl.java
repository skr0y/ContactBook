package model.dao;

import model.entities.Contact;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class ContactDAOFileImpl implements ContactDAO {
    private final String DEFAULT_FILENAME = "contacts.db";

    private String fileName = DEFAULT_FILENAME;
    private Set<Contact> contacts;

    public boolean update(Set<Contact> contacts) {
        this.contacts = contacts;
        return save();
    }

    public boolean save() {
        File dbFile = new File(fileName);
        try {
            if (!dbFile.exists() && !dbFile.createNewFile()) {
                throw new IOException("Output file is inaccessible");
            }
            FileOutputStream fos = new FileOutputStream(dbFile, false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(contacts);
            oos.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean load() {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            contacts = (HashSet<Contact>)ois.readObject();
        } catch (IOException e) {
            return false;
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    public Set<Contact> getAll() {
        if (contacts == null) {
            load();
        }
        return contacts;
    }
}
