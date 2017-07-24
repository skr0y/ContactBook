package controller;

import model.Model;
import model.dao.ContactDAO;
import model.entities.Contact;
import model.entities.EntityFactory;
import model.entities.Group;
import util.Observer;

import java.util.Set;
import java.util.stream.Collectors;

public class ContactController {
    private Model model;

    private ContactDAO dao;
    private EntityFactory factory;

    ContactController(Model model) {
        this.model = model;
        dao = model.getDAOProvider().getContactDAO();
        factory = model.getFactory();
    }

    public void addObserver(Observer observer) {
        dao.addObserver(observer);
    }

    public Contact getNew() {
        return (Contact)model.getFactory().getContact();
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

    public Set<Contact> getAll() {
        return dao.getAll();
    }

    public Set<Contact> getByGroup(Group group) {
        return getAll().stream().filter(x -> x.getGroup().equals(group)).collect(Collectors.toSet());
    }
}
