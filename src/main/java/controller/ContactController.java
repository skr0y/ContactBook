package controller;

import model.Model;
import model.dao.ContactDAO;
import model.dao.GroupDAO;
import model.entities.Contact;
import model.entities.EntityFactory;
import model.entities.Group;
import util.Observer;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ContactController {
    private Model model;

    private ContactDAO dao;
    private GroupDAO groupDAO;
    private EntityFactory factory;

    ContactController(Model model) {
        this.model = model;
        dao = model.getDAOProvider().getContactDAO();
        groupDAO = model.getDAOProvider().getGroupDAO();
        factory = model.getFactory();
    }

    public void addObserver(Observer observer) {
        dao.addObserver(observer);
    }

    public boolean add(Map<String, Object> params) {
        Set<Contact> contacts = new HashSet<>(dao.getAll());
        Contact newContact;
        Group newGroup = groupDAO.getAll().stream().filter(x -> x.getGroupName().equals(params.get("groupName"))).findFirst().orElse(null);
        params.remove("groupName");
        params.put("group", newGroup);
        newContact = (Contact)factory.getContact(params);
        contacts.add(newContact);
        return dao.update(contacts);
    }

    public boolean delete(Map<String, Object> params) {
        Set<Contact> contacts = new HashSet<>(dao.getAll());
        Contact delContact = null;
        for (Contact contact : contacts) {
            if (contact.getFirstName().equals(params.get("firstName"))
                    && contact.getLastName().equals(params.get("lastName"))
                    && contact.getPhoneNumber().equals(params.get("phoneNumber"))
                    && ((contact.getGroup() == null && params.get("groupName") == null) || (contact.getGroup().getGroupName().equals(params.get("groupName"))))) {
                delContact = contact;
                break;
            }
        }
        if (delContact != null) {
            contacts.remove(delContact);
            return dao.update(contacts);
        }
        return false;
    }

    public boolean update(Map<String, Object> params) {
        Set<Contact> contacts = new HashSet<>(dao.getAll());
        Contact oldContact = null;
        Contact newContact;
        Group newGroup;

        for (Contact contact : contacts) {
            if (contact.getFirstName().equals(params.get("oldFirstName"))
                    && contact.getLastName().equals(params.get("oldLastName"))
                    && contact.getPhoneNumber().equals(params.get("oldPhoneNumber"))
                    && ((contact.getGroup() == null && params.get("oldGroupName") == null) || (contact.getGroup().getGroupName().equals(params.get("oldGroupName"))))) {
                oldContact = contact;
                break;
            }
        }
        if (oldContact == null) {
            return false;
        }
        contacts.remove(oldContact);

        newGroup = groupDAO.getAll().stream().filter(x -> x.getGroupName().equals(params.get("groupName"))).findFirst().orElse(null);
        params.remove("groupName");
        params.put("group", newGroup);
        newContact = (Contact)factory.getContact(params);
        contacts.add(newContact);
        return dao.update(contacts);
    }

    public Set<Contact> getAll() {
        return dao.getAll();
    }

    public Set<Contact> getByGroup(Group group) {
        return getAll().stream().filter(x -> x.getGroup().equals(group)).collect(Collectors.toSet());
    }
}
