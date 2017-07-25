package model.entities;

import java.util.Map;

public class EntityFactory {
    public Entity getGroup(Map<String, Object> params) {
        Group group = new Group();
        group.setGroupName((String) params.get("groupName"));
        return group;
    }

    public Entity getContact(Map<String, Object> params) {
        Contact contact = new Contact();
        contact.setFirstName((String) params.get("firstName"));
        contact.setLastName((String) params.get("lastName"));
        contact.setPhoneNumber((String) params.get("phoneNumber"));
        contact.setGroup((Group) params.get("group"));
        return contact;
    }
}
