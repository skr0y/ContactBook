package model.entities;

import java.util.Map;

public class EntityFactory {
    public Entity getGroup(Map<String, Object> params) {
        Group group = new Group();
        if (params.containsKey("id")) {
            group.setId((int) params.get("id"));
        }
        group.setGroupName((String) params.get("groupName"));
        group.setUserId((int) params.get("userId"));
        return group;
    }

    public Entity getContact(Map<String, Object> params) {
        Contact contact = new Contact();
        if (params.containsKey("id")) {
            contact.setId((int) params.get("id"));
        }
        contact.setFirstName((String) params.get("firstName"));
        contact.setLastName((String) params.get("lastName"));
        contact.setPhoneNumber((String) params.get("phoneNumber"));
        if (params.containsKey("groupId")) {
            contact.setGroupId((int) params.get("groupId"));
        }
        if (params.containsKey("userId")) {
            contact.setGroupId((int) params.get("userId"));
        }
        return contact;
    }

    public Entity getUser(Map<String, Object> params) {
        User user = new User();
        if (params.containsKey("id")) {
            user.setId((int) params.get("id"));
        }
        user.setLogin((String) params.get("login"));
        user.setPassword((String) params.get("password"));
        return user;
    }
}
