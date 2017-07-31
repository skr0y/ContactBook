package model.entities;

import java.util.Map;

public class EntityFactory {
    public Entity getGroup(Map<String, Object> params) {
        Group group = new Group();
        if (params.containsKey("groupId")) {
            group.setId((int) params.get("groupId"));
        } else {
            group.setId();
        }
        group.setGroupName((String) params.get("groupName"));
        return group;
    }

    public Entity getContact(Map<String, Object> params) {
        Contact contact = new Contact();
        if (params.containsKey("contactId")) {
            contact.setId((int) params.get("contactId"));
        } else {
            contact.setId();
        }
        contact.setFirstName((String) params.get("firstName"));
        contact.setLastName((String) params.get("lastName"));
        contact.setPhoneNumber((String) params.get("phoneNumber"));
        if (params.containsKey("groupId")) {
            contact.setGroupId((int) params.get("groupId"));
        }
        return contact;
    }
}
