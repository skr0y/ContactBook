package model.dao;

import model.entities.Contact;

import java.util.Set;

public interface ContactDAO {
    boolean load();
    boolean save();
    boolean update(Set<Contact> contacts);
    Set<Contact> getAll();
}
