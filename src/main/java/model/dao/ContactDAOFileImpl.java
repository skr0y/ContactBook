package model.dao;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import model.entities.Contact;
import util.Observer;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class ContactDAOFileImpl implements ContactDAO {
    private final String DEFAULT_FILENAME = "contacts.db";

    private String fileName = DEFAULT_FILENAME;
    private ObservableSet<Contact> contacts;

    ContactDAOFileImpl() {
        contacts = FXCollections.observableSet(new HashSet<Contact>());
    }

    public void addObserver(Observer observer) {
        contacts.addListener((InvalidationListener) o -> observer.update(o));
        load();
    }

    public boolean update(Set<Contact> contacts) {
        this.contacts.clear();
        this.contacts.addAll(contacts);
        return save();
    }

    public boolean save() {
        File dbFile = new File(fileName);
        try {
            dbFile.createNewFile();
            try (FileOutputStream fos = new FileOutputStream(dbFile, false);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
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

    public boolean load() {
        try (FileInputStream fis = new FileInputStream(fileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Contact input;
            contacts.clear();
            while ((input = (Contact)ois.readObject()) != null) {
                contacts.add(input);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Set<Contact> getAll() {
        if (contacts.isEmpty()) {
            load();
        }
        return contacts;
    }
}
