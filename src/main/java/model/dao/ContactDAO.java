package model.dao;

import model.entities.Contact;
import util.Observer;

import java.util.Set;

public interface ContactDAO {
    void addObserver(Observer observer);
    boolean load();
    boolean save();
    boolean update(Set<Contact> contacts);
    Set<Contact> getAll();
}
