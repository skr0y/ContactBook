package service;

import dao.ContactDAO;
import entities.Contact;
import entities.EntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("contactService")
@Transactional
public class ContactService {

    @Autowired
    private ContactDAO contactDAO;

    @Autowired
    private EntityFactory entityFactory;

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

    public List<Map<String, Object>> getAll() {
        List<Map<String, Object>> all = new ArrayList<>();
        for (Contact contact : contactDAO.getAll()) {
            Map<String, Object> contactMap = new HashMap<>();
            contactMap.put("id", contact.getId());
            contactMap.put("firstName", contact.getFirstName());
            contactMap.put("lastName", contact.getLastName());
            contactMap.put("phoneNumber", contact.getPhoneNumber());
            contactMap.put("groupId", contact.getGroup().getId());
            all.add(contactMap);
        }
        return all;
    }

    public List<Map<String, Object>> getAll(int userId) {
        return getAll().stream().filter(x -> (int) x.get("userId") == userId).collect(Collectors.toList());
    }
}
