package model.entities;

import java.io.Serializable;

public class Contact extends Entity implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int groupId = -1;
    private int userId = -1;

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

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

    public int getGroupId() {
        return groupId;
    }

    void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void removeGroup() { this.groupId = -1; }

    public int getUserId() {
        return userId;
    }

    void setUserId(int userId) {
        this.userId = userId;
    }
}
