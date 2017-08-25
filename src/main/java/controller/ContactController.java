package controller;

import model.Model;
import model.dao.ContactDAO;
import model.entities.Contact;
import model.entities.EntityFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ContactController {
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
        return contactDAO.add(newContact);
    }

    public boolean delete(int contactId) {
        Contact contact = contactDAO.get(contactId);
        return contact != null && contactDAO.delete(contact);
    }

    public boolean update(Map<String, Object> params) {
        Contact contact = (Contact) entityFactory.getContact(params);
        return contactDAO.update(contact);
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

    public Set<Map<String, Object>> getAll(int userId) {
        return getAll().stream().filter(x -> (int) x.get("userId") == userId).collect(Collectors.toSet());
    }
}
