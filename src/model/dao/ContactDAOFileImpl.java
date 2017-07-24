package model.dao;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.ObservableSet;
import model.entities.Contact;
import util.Observer;

import java.io.*;
import java.util.Set;

public class ContactDAOFileImpl implements ContactDAO {
    private final String DEFAULT_FILENAME = "contacts.db";

    private String fileName = DEFAULT_FILENAME;
    private ObservableSet<Contact> contacts;

    public void addObserver(Observer observer) {
        contacts.addListener(new InvalidationListener() {
            public void invalidated(Observable o) {
                observer.update(o);
            }
        });
    }

    public boolean update(Set<Contact> contacts) {
        this.contacts = (ObservableSet<Contact>) contacts;
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
            contacts = new SimpleSetProperty<>();
            contacts.addAll((ObservableSet<Contact>) ois.readObject());
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
