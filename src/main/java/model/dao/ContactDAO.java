package model.dao;

import model.entities.Contact;
import model.entities.Group;

import java.util.Set;

/**
 * Provides access to data storage for contacts
 */
public interface ContactDAO {

    /**
     * Add a contact to the storage
     * @param contact Contact object to be added
     * @return True if operation succeeds
     */
    boolean add(Contact contact);

    /**
     * Update a contact in the storage
     * @param contact Contact object to be added
     * @return True if operation succeeds
     */
    boolean update(Contact contact);

    /**
     * Delete a contact from the storage
     * @param contact Contact object to be added
     * @return True if operation succeeds
     */
    boolean delete(Contact contact);

    /**
     * Remove a group from all contacts
     * @param group Group object to be removed
     * @return True if operation succeeds
     */
    boolean deleteGroup(Group group);

    /**
     * Load a group from the storage by its ID
     * @param id ID of the contact
     * @return Requested Contact object
     */
    Contact get(int id);

    /**
     * Loads all contacts from the storage
     * @return Set of all Contact objects
     */
    Set<Contact> getAll();

}
