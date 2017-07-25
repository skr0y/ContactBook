package model.entities;

import java.io.Serializable;

public class Contact extends Entity implements Serializable {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Group group;

    public String getFirstName() {
        return firstName;
    }

    void setFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name is not specified");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getToString() {
        return String.format("%1s %2s", firstName, lastName).trim();
    }

    public Group getGroup() {
        return group;
    }

    void setGroup(Group group) {
        this.group = group;
    }
}
