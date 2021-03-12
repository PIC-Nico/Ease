package model;

import controller.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.prefs.Preferences;

public class Users {
    // singleton pattern object
    private static final Users OBJ = new Users();

    // Log4J2 logger
    private final Logger logger = LogManager.getLogger(Users.class);

    // preferences
    private final Preferences prefs = Preferences.userRoot().node(this.getClass().getName());

    // list to hold all users
    private ArrayList<User> users;

    // holds the remembered user (or null)
    private String remember;

    // holds the active user
    private String activeUser;

    // preference keys
    private final String USER_COUNT = "User:Count";
    private final String USER_NAME = "User:Name:";
    private final String USER_SALT = "User:Salt:";
    private final String USER_HASH = "User:Hash:"; // the hashed password
    private final String USER_REMEMBER = "User:Remember:";

    /**
     * Default constructor
     */
    private Users() {
        loadUsers();
    }

    /**
     * Public API to get the singleton pattern object.
     * @return The users object.
     */
    public static Users getInstance() {
        return OBJ;
    }

    /**
     * Load all available users from the preferences.
     */
    private void loadUsers() {
        users = new ArrayList<>();
        int n = prefs.getInt(USER_COUNT, 0);

        String name, salt, hash;

        for(int i=0; i<n; i++) {
            name = prefs.get(USER_NAME + i, null);
            salt = prefs.get(USER_SALT + i, null);
            hash = prefs.get(USER_HASH + i, null);

            // check if all values are available
            if(name != null || salt != null || hash != null) {
                users.add(new User(i, name, salt, hash));
                logger.info("User added (#" + i + ")");
            } else {
                logger.error("Corrupt user (" + i + ")");
            }
        }

        remember = prefs.get(USER_REMEMBER, null);
    }

    /**
     * This will check if given user is already available.
     * @param user Name of the user to check.
     * @return True if the user already exists otherwise false.
     */
    public boolean exists(String user) {
        boolean exists = false;

        for(User x : users) {
            if(x.getName().equals(user)) {
                exists = true;
            }
        }

        return exists;
    }

    /**
     * This will create a new user (if it does not already ecist).
     * @param name Name of the new user.
     * @param salt Salt of the new user.
     * @param hash Hash (password) of the new user.
     * @return Created successfully (true) otherwise false (e.g. because user already exist).
     */
    public boolean create(String name, String salt, String hash) {
        if(exists(name)) {
            return false;
        }

        // create the new user
        User user = new User(users.size(), name, salt, hash);

        // add the new user
        prefs.put(USER_NAME + users.size(), user.getName());
        prefs.put(USER_SALT + users.size(), user.getSalt());
        prefs.put(USER_HASH + users.size(), user.getHash());
        users.add(user);

        // update the total number of users
        prefs.putInt(USER_COUNT, users.size());

        return true;
    }

    /**
     * This will return with the salt of a given user.
     * @param user Name of the user to get its salt.
     * @return The salt fromm the user or null if user doesn't exist.
     */
    public String getSalt(String user) {
        for(User x : users) {
            if(x.getName().equals(user)) {
                return x.getSalt();
            }
        }

        return null;
    }

    /**
     * This will return with the hash of a given user.
     * @param user Name of the user to get its hash (password).
     * @return The hash fromm the user or null if user doesn't exist.
     */
    public String getHash(String user) {
        for(User x : users) {
            if(x.getName().equals(user)) {
                return x.getHash();
            }
        }

        return null;
    }

    /**
     * Get the last remembered user.
     * @return The rememberes user (null if not available).
     */
    public String getRemember() {
        return remember;
    }

    /**
     * This will set the remembered user.
     * @param user User name to remember (null to clear).
     */
    public void setRemember(String user) {
        if(users == null) {
            prefs.remove(USER_REMEMBER);
        } else {
            if(exists(user)) {
                prefs.put(USER_REMEMBER, user);
            }
        }
    }

    /**
     * This can be used to set the current active user.
     * Normally this will only be called once from the Login UI.
     * @param user User to set as active user.
     */
    public void setActiveUser(String user) {
        activeUser = user;
    }

    /**
     * This will return with the current active user.
     * @return The active user.
     */
    public String getActiveUser() {
        return activeUser;
    }
}
