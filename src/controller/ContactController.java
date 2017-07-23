package controller;

import model.Model;
import model.dao.ContactDAO;
import model.entities.Contact;
import model.entities.Group;
import view.View;

import java.util.Set;
import java.util.stream.Collectors;

public class ContactController {
    private Model model;
    private View view;

    private ContactDAO dao;

    ContactController(Model model, View view) {
        this.view = view;
        this.model = model;
        dao = model.getDAOProvider().getContactDAO();
    }

    public boolean add(Contact contact) {
        Set<Contact> contacts = dao.getAll();
        contacts.add(contact);
        return dao.update(contacts);
    }

    public boolean delete(Contact contact) {
        Set<Contact> contacts = dao.getAll();
        contacts.remove(contact);
        return dao.update(contacts);
    }

    public boolean update(Contact contact) {
        Set<Contact> contacts = dao.getAll();
        contacts.remove(contact);
        contacts.add(contact);
        return dao.update(contacts);
    }

    public Set<Contact> getByGroup(Group group) {
        Set<Contact> contacts = dao.getAll();
        return contacts.stream().filter(x -> x.getGroup().equals(group)).collect(Collectors.toSet());
    }
}
