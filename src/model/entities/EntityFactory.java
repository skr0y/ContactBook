package model.entities;

public class EntityFactory {
    public Entity getGroup() {
        Group group = new Group();
        return group;
    }

    public Entity getContact() {
        Contact contact = new Contact();
        return contact;
    }
}
