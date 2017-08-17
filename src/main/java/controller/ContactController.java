package controller;

import model.Model;
import model.dao.ContactDAO;
import model.entities.Contact;
import model.entities.EntityFactory;

import java.util.*;

public class ContactController extends Observable {
    private Model model;

    private ContactDAO contactDAO;
    private EntityFactory entityFactory;

    ContactController(Model model) {
        this.model = model;
        contactDAO = model.getDaoFactory().getContactDAO();
        entityFactory = model.getEntityFactory();
    }

    public boolean add(Map<String, Object> params) {
        Contact newContact = (Contact) entityFactory.getContact(params);
        boolean result = contactDAO.add(newContact);
        setChanged();
        notifyObservers();
        return result;
    }

    public boolean delete(Map<String, Object> params) {
        Contact contact = contactDAO.get((int) params.get("id"));
        boolean result = contact != null && contactDAO.delete(contact);
        setChanged();
        notifyObservers();
        return result;
    }

    public boolean update(Map<String, Object> params) {
        Contact contact = (Contact) entityFactory.getContact(params);
        boolean result = contactDAO.update(contact);
        setChanged();
        notifyObservers();
        return result;
    }

    public Set<Map<String, Object>> getAll() {
        Set<Map<String, Object>> all = new HashSet<>();
        for (Contact contact : contactDAO.getAll()) {
            Map<String, Object> contactMap = new HashMap<>();
            contactMap.put("id", contact.getId());
            contactMap.put("firstName", contact.getFirstName());
            contactMap.put("lastName", contact.getLastName());
            contactMap.put("phoneNumber", contact.getPhoneNumber());
            contactMap.put("groupId", contact.getGroupId());
            all.add(contactMap);
        }
        return all;
    }
}
