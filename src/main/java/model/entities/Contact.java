package model.entities;

import javax.persistence.*;
import java.io.Serializable;

@javax.persistence.Entity
@Table(name = "Contacts")
public class Contact extends Entity implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int groupId = -1;
    private int userId = -1;

    @Id
    @SequenceGenerator(name = "seq_gen_contact", sequenceName = "Contacts_ContactID_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq_gen_contact")
    @Column(name = "ContactID", nullable = false, unique = true)
    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    @Column(name = "FirstName", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    void setFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name is not specified");
        }
        this.firstName = firstName;
    }

    @Column(name = "LastName")
    public String getLastName() {
        return lastName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "PhoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "GroupID")
    public int getGroupId() {
        return groupId;
    }

    void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void removeGroup() { this.groupId = -1; }

    @Column(name = "UserID", nullable = false)
    public int getUserId() {
        return userId;
    }

    void setUserId(int userId) {
        this.userId = userId;
    }
}
