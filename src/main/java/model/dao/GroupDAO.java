package model.dao;

import model.entities.Group;

import java.util.Set;

/**
 * Provides access to data storage for groups
 */
public interface GroupDAO {

    /**
     * Add a group to the storage
     * @param group Group object to be added
     * @return True if operation succeeds
     */
    boolean add(Group group);

    /**
     * Update a group in the storage
     * @param group Group object to be updated
     * @return True if operation succeeds
     */
    boolean update(Group group);

    /**
     * Delete a group from the storage
     * @param group Group object to be deleted
     * @return True if operation succeeds
     */
    boolean delete(Group group);

    /**
     * Load a group from the storage by its ID
     * @param id ID of the group
     * @return Requested Group object
     */
    Group get(int id);

    /**
     * Load all groups from the storage
     * @return Set of all Group objects
     */
    Set<Group> getAll();
}
