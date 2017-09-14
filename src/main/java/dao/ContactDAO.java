package dao;

import entities.Contact;

import java.util.List;

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
     * @param contact Contact object to be updated
     * @return True if operation succeeds
     */
    boolean update(Contact contact);

    /**
     * Delete a contact from the storage
     * @param contact Contact object to be deleted
     * @return True if operation succeeds
     */
    boolean delete(Contact contact);

    /**
     * Remove a group from all contacts
     * @param groupId ID of a group that needs to be removed
     * @return True if operation succeeds
     */
    boolean deleteGroup(int groupId);

    /**
     * Load a contact from the storage by its ID
     * @param id ID of the contact
     * @return Requested Contact object
     */
    Contact get(int id);

    /**
     * Loads all contacts from the storage
     * @return List of all Contact objects
     */
    List<Contact> getAll();

}
