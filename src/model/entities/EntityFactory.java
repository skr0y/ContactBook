package model.entities;

public class EntityFactory {
    public static Entity getGroup() {
        Group group = new Group();
        return group;
    }

    public static Entity getContact() {
        Contact contact = new Contact();
        return contact;
    }
}
