package dao;

/**
 * Provides access to stored procedures in database
 */
public interface AdminDAO {

    /**
     * Get total user count
     * @return User count
     */
    int totalUsers();

    /**
     * Get contact count for a specific user
     * @param userId ID of the user
     * @return Contact count
     */
    int contactsCountByUser(int userId);

    /**
     * Get group count for a specific user
     * @param userId ID of the user
     * @return Group count
     */
    int groupsCountByUser(int userId);

    /**
     * Get average number of contacts per group
     * @return Average number
     */
    int averageContactsPerGroup();

    /**
     * Get average number of contacts per user
     * @return Average number
     */
    int averageContactsPerUser();

    /** Get number of users with less that 10 contacts
     * @return Number of users
     */
    int inactiveUsersCount();

}
