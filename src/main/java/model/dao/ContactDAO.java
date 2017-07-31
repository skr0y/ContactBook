package model.dao;

import model.entities.Contact;
import model.entities.Group;

import java.util.Set;

public interface ContactDAO {
    boolean add(Contact contact);
    boolean update(Contact contact);
    boolean delete(Contact contact);
    boolean deleteGroup(Group group);
    Contact get(int id);
    Set<Contact> getAll();

}
