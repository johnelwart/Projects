/**
 * This class is a blueprint for User objects, which will be used to represent users of the application. These users can
 * be one of two types, individual or organization. Every user contains attributes representing their first name, last
 * name, account username, account password, and whether they are an organization. If the user is an organization,
 * their firstName attribute represents the organization name, and their lastName attribute is null.
 *
 * @author Danny Bodin, John Elwart, Tucker Dickson
 * @version 1.0
 * @since 12/3/21
 */
public class User {
    /**
     * This private String represents either the first name of an individual user or the organization name of an
     * organization user.
     */
    private String firstName;

    /**
     * This private either represents the last name of an individual user or is null for an organization user.
     */
    private String lastName;

    /**
     * This private String represents the account username of a user.
     */
    private String username;

    /**
     * This private String represents the account password of a user.
     */
    private String password;

    /**
     * This private boolean represents whether or not a user is an organization (true), or an individual (false).
     */
    private boolean isOrganization;

    /**
     * This is the first of two constructors for the User class. This constructor takes in five arguments and uses them
     * to initialize their respective private member variable.
     *
     * @param firstName This String represents the first name of the user (individual) or the name of the organization
     *                  (organization).
     * @param lastName This String either represents the last name of the user (individual) or is null (organization).
     * @param username This String represents the user's account username.
     * @param password This String represents the user's account password.
     * @param isOrganization This boolean indicated whether a user is an organization (true) or an individual (false).
     */
    public User(String firstName, String lastName, String username, String password, boolean isOrganization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.isOrganization = isOrganization;
    }

    /**
     * This is the second of the two constructors for the User class. This constructor takes in two Strings as arguments
     * and uses them to initialize firstName and lastName.
     *
     * @param firstName This String represents the first name of the user (individual) or the name of the organization
     *                  (organization).
     * @param lastName This String either represents the last name of the user (individual) or is null (organization).
     */
    public User(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * This is the getter for the firstName private member variable.
     *
     * @return This method returns a String representing the first name of the User (individual) or organization name
     * (organization)
     */
    public String getFirstName() { return firstName; }

    /**
     * This is the getter for the lastName private member variable.
     *
     * @return This method returns a String representing the last name of the User (individual) or null (organization)
     */
    public String getLastName() { return lastName; }

    /**
     * This is the getter for the username private member variable.
     *
     * @return This method returns a String representing the User's username.
     */
    public String getUsername() { return username; }

    /**
     * This is the getter for the password private member variable.
     *
     * @return This method returns a String representing the User's password.
     */
    public String getPassword() { return password; }

    /**
     * This is the getter for the isOrganization private member variable.
     *
     * @return This method returns a boolean value indicating whether a User is an individual (false) or an organization
     * (true).
     */
    public boolean isOrganization() { return isOrganization; }

    /**
     * This method returns the String representation of a User object. This String will assume the following form:
     * "firstName lastName".
     *
     * @return This method returns the String representation of a User object.
     */
    public String toString(){
        return firstName + " " + lastName;
    }
}
