package dao;

import entities.User;

import java.util.List;

/**
 * Provides access to data storage for users
 */
public interface UserDAO {

    /**
     * Add a user to the storage
     * @param user User object to be added
     * @return True if operation succeeds
     */
    boolean add(User user);

    /**
     * Update a user in the storage
     * @param user User object to be updated
     * @return True if operation succeeds
     */
    boolean update(User user);

    /**
     * Delete a user from the storage
     * @param user User object to be deleted
     * @return True if operation succeeds
     */
    boolean delete(User user);

    /**
     * Load a user from the storage by its ID
     * @param id ID of the user
     * @return Requested User object
     */
    User get(int id);

    /**
     * Load a user from the storage by its login
     * @param login user login
     * @return Requested User object
     */
    User get(String login);

    /**
     * Checks if login-password combination is valid
     * @param login user login
     * @param password user password
     * @return True if login and password are valid
     */
    boolean checkCredentials(String login, String password);

    /**
     * Loads all users from the storage
     * @return List of all User objects
     */
    List<User> getAll();

}
