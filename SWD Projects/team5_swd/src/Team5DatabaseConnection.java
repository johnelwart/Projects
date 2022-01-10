import java.sql.*;
import java.util.ArrayList;

/**
 * This class is responsible for connecting to, querying from, and modifying the database. This class is used within the
 * program to insert new users/events into the database and retrieve information about user/events to populate different
 * screens.
 *
 * @author Danny Bodin, John Elwart, Tucker Dickson
 * @version 1.0
 * @since 12/3/21
 */
public class Team5DatabaseConnection {
    /**
     * This private String holds the url address necessary to establish a connection with the database.
     */
    private static final String URL = "jdbc:mysql://s-l112.engr.uiowa.edu:3306/swd_db025";

    /**
     * This private String holds the username needed to access the database.
     */
    private static final String USERNAME = "swd_group025";

    /**
     * This private String holds the password needed to access the database.
     */
    private static final String PASSWORD = "swd_team5";

    /**
     * This private Connection object is the application's connection to the database.
     */
    private final Connection databaseConnection;

    /**
     * This is the sole constructor for the Team5DatabaseConnection class. It doesn't take in any arguments, and it uses
     * URL, USERNAME, and PASSWORD to establish a connection to the database, which is stored in databaseConnection.
     *
     * @throws SQLException This exception is thrown if there is an issue accessing the database with the provided URL,
     *                      USERNAME, and/or PASSWORD.
     */
    public Team5DatabaseConnection() throws SQLException {
        databaseConnection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    /**
     * This method is used to query the USERS table on a username. If the user is found, it returns a new user object
     * containing all of the information pulled from the USERS table.
     *
     * @param username This String will be used to query the USERS table.
     * @return This method returns a new User object containing the information pulled from the USERS table.
     * @throws SQLException This exception is thrown if a database access error occurs when the executeQuery() method is
     *                      called.
     */
    public User findUser(String username) throws SQLException {
        // use our databaseConnection to create a new Statement
        Statement stmt = databaseConnection.createStatement();

        // use the Statement to execute the query below; store the results in a ResultSet object
        ResultSet rs = stmt.executeQuery( String.format("select * from USERS where username = '%s';", username));

        // ResultSet is initially positioned before the first row, so we need to move it to the first row
        rs.next();

        // construct and return a new User object with all of the information pulled from the ResultSet row
        return new User(rs.getString("firstName"), rs.getString("lastName"), rs.getString("username"),
                rs.getString("password"), rs.getBoolean("isOrganization"));
    }

    /**
     * This method is used to insert rows into the USERS and PERSONAL_INFO tables. This method is called whenever a new
     * user is registered in the application.
     *
     * @param firstname This String will be used to populate the firstName field in the USERS table for the new row.
     * @param lastname This String will be used to populate the lastName field in the USERS table for the new row.
     * @param username This String will be used to populate the username fields in both the USERS and PERSONAL_INFO tables
     *                 for the new rows.
     * @param password This String will be used to populate the password field in the USERS table for the new row.
     * @param isOrganization This boolean will be used to populate the isOrganization field in the USERS table for the new
     *                       row.
     * @param dateOfBirth This is the date of birth of the user.
     * @throws SQLException This exception is thrown if a database access error occurs when the executeQuery() method is
     *                      called.
     */
    public void createNewUser(String firstname, String lastname, String username, String password, boolean isOrganization, Date dateOfBirth) throws SQLException {
        // use our databaseConnection to create a new statement
        Statement stmt = databaseConnection.createStatement();

        // use the statement to execute the following insert statement on the USERS table
        stmt.executeUpdate(String.format("insert into USERS(firstname, lastname, username, password, isOrganization) " +
                "values('%s', '%s', '%s', '%s', %s);", firstname, lastname, username, password, (isOrganization) ? 1 : 0));

        // if the user is an individual, execute the following insert statement on the PERSONAL_INFO table (orgs don't
        // have personal info)
        if(!isOrganization) {
            stmt.executeUpdate(String.format("insert into PERSONAL_INFO(username, DOB, vaccinationID, recentTest) " +
                    "values('%s', '%s', %s, %s);", username, dateOfBirth.toString(), 1, null));
        }
    }

    /**
     * This method is used to retrieve a date of birth from the PERSONAL_INFO table, given a username.
     *
     * @param username This is the username that we will query the PERSONAL_INFO table on for a date of birth.
     * @return This method returns a java.sql.Date object, representing the date of birth for the given username.
     * @throws SQLException This exception is thrown if a database access error occurs when the executeQuery() method is
     *                      called.
     */
    public Date getDOB(String username) throws SQLException {
        // use the databaseConnection to create a new Statement
        Statement stmt = databaseConnection.createStatement();

        // query the PERSONAL_INFO table for the DOB of the given username
        ResultSet rs = stmt.executeQuery( String.format("select DOB from PERSONAL_INFO where username = '%s';", username));

        // grab the first row returned
        rs.next();

        // return the DOB of the line
        return rs.getDate("DOB");
    }

    /**
     * This method is used to update the information in the USERS table for a specific username.
     *
     * @param username This String is the username that we will update the USERS table on.
     * @param firstName This String is what the firstName field will be updated to.
     * @param lastName This String is what the lastName field will be updated to.
     * @param password This String is what the password field will be updated to.
     * @param date This Date is what the DOB will be updated to.
     * @throws SQLException This exception is thrown if a database access error occurs when the executeQuery() method is
     *                      called.
     */
    public void updateUser(String username, String firstName, String lastName, String password, Date date) throws SQLException {
        // use the databaseConnection to create a new Statement for our update
        Statement stmt = databaseConnection.createStatement();

        // execute the following update statement on the USERS table, joined with the PERSONAL_INFO table on the username
        stmt.executeUpdate(String.format("update USERS left join PERSONAL_INFO on USERS.username = PERSONAL_INFO.username " +
                "set USERS.firstname = '%s', USERS.lastname = '%s', USERS.password = '%s', PERSONAL_INFO.DOB = '%s' " +
                "where USERS.username = '%s';", firstName, lastName, password, date.toString(), username));
    }

    /**
     * This method verifies that the username and password pair passed in matches a row in the database. This method is
     * used in the sign in page to make sure that the login credentials are valid. If a user exists in the database,
     * this method returns true, otherwise it throws an exception.
     *
     * @param username This String will be used to query the USERS table (via the findUser() method).
     * @param password This String will be used to compare against the password returned from the USERS table.
     * @return This method returns true if a matching username was found in the USERS table and the password passed in
     *          matches the password in the table (returns false otherwise).
     * @throws SQLException This exception is thrown if a database access error occurs when the executeQuery() method is
     *                      called, or if there isn't any rows in the USERS table with the given username.
     */
    public boolean verifyUser(String username, String password) throws SQLException {
        User user = this.findUser(username);
        return user.getPassword().equals(password);
    }

    /**
     * This method is used to determine whether a given username is attached to an individual account or an organization
     * account. It does this by calling the findUser() method and then checking the isOrganization attribute of the User
     * returned.
     *
     * @param username This String will be passed into the findUser() method (which will use it to query the USERS table).
     * @return This method returns true if the username provided is an organization and false otherwise.
     * @throws SQLException This exception is thrown if a database access error occurs when the executeQuery() method is
     *                      called, or if there isn't any rows in the USERS table with the given username.
     */
    public boolean isOrganization(String username) throws SQLException {
        return this.findUser(username).isOrganization();
    }

    /**
     * This method is used to insert a row into the EVENTS table. This method is called whenever an organization creates
     * a new event from within the organization homepage.
     *
     * @param event This Event object represents the event that will be inserted into the EVENTS table.
     * @throws SQLException This exception is thrown if a database access error occurs when the executeQuery() method is
     *                      called.
     */
    public void createNewEvent(Event event) throws SQLException {
        // use the databaseConnection to create a new Statement
        Statement stmt = databaseConnection.createStatement();

        // use the statement to execute the following insert statement on the EVENTS table
        stmt.executeUpdate(String.format("insert into EVENTS(name, organizerUsername, location, capacity, needsVaccination," +
                        " needsBooster, testReplacement, needsMask, date, hours, minutes) values('%s', '%s', '%s', %s, %s," +
                        " %s, %s, %s, '%s', %s, %s);", event.getEventName(), event.getOrganizerUsername(), event.getLocation(),
                event.getCapacity(), (event.isNeedsVaccine()) ? 1 : 0, (event.isNeedsBooster()) ? 1 : 0,
                (event.isTestReplacement()) ? 1 : 0, (event.isNeedsMask()) ? 1 : 0, event.getDate().toString(),
                event.getHours(), event.getMinutes()));
    }

    /**
     * This method is used to update information on an event in the EVENTS table. This method is called whenever an organizer
     * updates event information from within the event detail page.
     *
     * @param event This Event object represents the event that will be updated in the EVENTS table.
     * @throws SQLException This exception is thrown if a database access error occurs when the executeQuery() method is
     *                      called.
     */
    public void updateEvent(Event event) throws SQLException {
        // use the databaseConnection to create a new Statement
        Statement stmt = databaseConnection.createStatement();

        // use the Statement to execute the following update statement on the EVENTS table
        stmt.executeUpdate(String.format("update EVENTS set EVENTS.location = '%s', EVENTS.capacity = %s, EVENTS.needsVaccination = %s, EVENTS.needsBooster = %s, " +
                "EVENTS.testReplacement = %s, EVENTS.needsMask = %s, EVENTS.date = '%s', EVENTS.hours = %s, EVENTS.minutes = %s " +
                "where EVENTS.name = '%s';", event.getLocation(), event.getCapacity(), (event.isNeedsVaccine()) ? 1 : 0, (event.isNeedsBooster()) ? 1 : 0, (event.isTestReplacement()) ? 1 : 0,
                (event.isNeedsMask()) ? 1 : 0, event.getDate().toString(), event.getHours(), event.getMinutes(), event.getEventName()));
    }

    /**
     * This method is used to insert a new row into the REGISTRATION table. This method is called whenever an individual
     * user registers for an event from within the individual home page.
     *
     * @param username This String is the username of the individual registering for the event.
     * @param eventName This String is the name of the event that the individual is registering for.
     * @throws SQLException This exception is thrown if a database access error occurs when the executeQuery() method is
     *                      called.
     */
    public void createRegistration(String username, String eventName) throws SQLException {
        // use the databaseConnection to create a new Statement
        Statement stmt = databaseConnection.createStatement();

        // use the Statement to execute the following insert statement on the REGISTRATION table
        stmt.executeUpdate(String.format("insert into REGISTRATION(username, eventName) values('%s', '%s');", username, eventName));
    }


    /**
     * This method is used to get all of the events that a certain individual username is currently registered for. This method is
     * used to populate the current registration listview on the individual home page.
     *
     * @param username This String is used to query the REGISTRATION table.
     * @return This method returns an ArrayList of Event object containing all of the registrations associated with the
     *          given username in the REGISTRATION table.
     * @throws SQLException This exception is thrown if a database access error occurs when the executeQuery() method is
     *                      called.
     */
    public ArrayList<Event> getUsersEvents(String username) throws SQLException {
        // create a new ArrayList to store returned events
        ArrayList<Event> events = new ArrayList<>();

        // use the databaseConnection to create a new Statement
        Statement stmt = databaseConnection.createStatement();

        // use the Statement to execute the following query on the REGISTRATION table.
        ResultSet rs = stmt.executeQuery(String.format("select * from REGISTRATION left join EVENTS on REGISTRATION.eventName = EVENTS.name where REGISTRATION.username = '%s';", username));

        // Statement.executeQuery() will return all rows tied to the given username
        // iterate over all rows returned, adding a new event to the ArrayList for each one
        while(rs.next()){
            events.add(new Event(rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getBoolean(7), rs.getBoolean(8), rs.getBoolean(9), rs.getBoolean(10), rs.getDate(11), rs.getInt(12), rs.getInt(13)));
        }

        // return the ArrayList
        return events;
    }

    /**
     * This method is used to get all of the events for a specific organizer from the REGISTRATION table. This method is
     * called from within the organizer homepage to populate their list of events.
     *
     * @param username This String represents the organizerUsername and will be used to query the REGISTRATION table.
     * @return This method returns an ArrayList of Event objects containing all of the events organized by the passed in
     *          (organizer)username.
     * @throws SQLException This exception is thrown if a database access error occurs when the executeQuery() method is
     *                      called.
     */
    public ArrayList<Event> getEvents(String username) throws SQLException {
        // create a new ArrayList to store the returned results
        ArrayList<Event> events = new ArrayList<>();

        // use the databaseConnection to create a new Statement
        Statement stmt = databaseConnection.createStatement();

        // use the statement to execute the following query on the EVENTS table
        ResultSet rs = stmt.executeQuery( String.format("select * from EVENTS where organizerUsername = '%s';", username));

        // Statement.executeQuery() will return all rows tied to the given username
        // iterate over all rows returned, adding a new event to the ArrayList for each one
        while(rs.next()){
            events.add(new Event(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getBoolean(5), rs.getBoolean(6), rs.getBoolean(7), rs.getBoolean(8), rs.getDate(9), rs.getInt(10), rs.getInt(11)));
        }

        // return the ArrayList
        return events;
    }

    public ArrayList<Event> getAllEvents() throws SQLException {
        ArrayList<Event> events = new ArrayList<>();

        Statement stmt = databaseConnection.createStatement();

        ResultSet rs = stmt.executeQuery( String.format("select * from EVENTS;"));

        while(rs.next()){
            events.add(new Event(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getBoolean(5), rs.getBoolean(6), rs.getBoolean(7), rs.getBoolean(8), rs.getDate(9), rs.getInt(10), rs.getInt(11)));
        }

        return events;
    }

    /**
     * This method is used to delete an event from the EVENTS and REGISTRATION tables. This method is called whenever the
     * 'Delete event' button is pressed from within the event detail page.
     *
     * @param eventName This String stores the name of the event that is to be deleted.
     * @throws SQLException This exception is thrown if a database access error occurs when the executeQuery() method is
     *                      called.
     */
    public void deleteEvent(String eventName) throws SQLException {
        // use the databaseConnection to create a new Statement
        Statement stmt = databaseConnection.createStatement();

        // use the Statement to execute the following two delete statements on the EVENTS table and REGISTRATION table
        stmt.executeUpdate(String.format("delete from EVENTS where EVENTS.name = '%s';", eventName));
        stmt.executeUpdate(String.format("delete from REGISTRATION where REGISTRATION.eventName = '%s';", eventName));
    }

    /**
     * This method is used to get an ArrayList of all of the Users attending a specific event. This method is called from
     * within the Event detail screen.
     *
     * @param eventName This String is the event name that will be used to query REGISTRATION table.
     * @return This method returns an ArrayList of User objects, representing all of the (individual) users attending
     *          an event.
     * @throws SQLException This exception is thrown if a database access error occurs when the executeQuery() method is
     *                      called.
     */
    public ArrayList<User> getAttendeesForEvent(String eventName) throws SQLException {
        // create a new ArrayList to store returned results
        ArrayList<User> attendees = new ArrayList<>();

        // use databaseConnection to create a new Statement
        Statement stmt = databaseConnection.createStatement();

        // use the Statement to execute the following query on the REGISTRATION table (joined with the USERS table)
        ResultSet rs = stmt.executeQuery(String.format("select USERS.firstName, USERS.lastName from REGISTRATION left join USERS on REGISTRATION.username = USERS.username " +
                "where REGISTRATION.eventName = '%s';", eventName));

        // Statement.executeQuery() will return all rows tied to the given username
        // iterate over all rows returned, adding a new event to the ArrayList for each one
        while(rs.next()){
            attendees.add(new User(rs.getString(1), rs.getString(2)));
        }

        // return the ArrayList
        return attendees;
    }

    public void updateNegativeTest(String username, Date date) throws SQLException {
        Statement stmt = databaseConnection.createStatement();

        stmt.executeUpdate(String.format("update PERSONAL_INFO set PERSONAL_INFO.recentTest = '%s' where PERSONAL_INFO.username = '%s';", date.toString(), username));
    }

    public void createVaccine(String username, Vaccine vaccine) throws SQLException {
        Statement stmt = databaseConnection.createStatement();

        stmt.executeUpdate(String.format("update PERSONAL_INFO set PERSONAL_INFO.vaccinationID = %s where PERSONAL_INFO.username = '%s';", vaccine.getVaccinationID(), username));
        stmt.executeUpdate(String.format("insert into VACCINES(vaccinationID, hasFirstDose, hasSecondDose, hasBooster) values (%s, %s, %s, %s);", vaccine.getVaccinationID(), (vaccine.isHasFirstDose()) ? 1 : 0, (vaccine.isHasSecondDose()) ? 1 : 0, (vaccine.isHasBooster()) ? 1 : 0));
    }

    public Vaccine getUsersVaccine(String username) throws SQLException {
        Statement stmt = databaseConnection.createStatement();

        ResultSet rs = stmt.executeQuery(String.format("select VACCINES.vaccinationID, VACCINES.hasFirstDose, VACCINES.hasSecondDose, VACCINES.hasBooster from " +
                "PERSONAL_INFO left join VACCINES on VACCINES.vaccinationID = PERSONAL_INFO.vaccinationID " +
                "where PERSONAL_INFO.username = '%s';", username));
        rs.next();
        return new Vaccine(rs.getInt(1), rs.getBoolean(2), rs.getBoolean(3), rs.getBoolean(4));
    }

    public void updateVaccine(Vaccine vaccine) throws SQLException {
        Statement stmt = databaseConnection.createStatement();

        stmt.executeUpdate(String.format("update VACCINES set VACCINES.hasFirstDose = %s, VACCINES.hasSecondDose = %s, VACCINES.hasBooster = %s " +
                "where VACCINES.vaccinationID = %s;", (vaccine.isHasFirstDose()) ? 1 : 0, (vaccine.isHasSecondDose()) ? 1 : 0, (vaccine.isHasBooster()) ? 1 : 0, vaccine.getVaccinationID()));
    }

    public boolean doesUserHaveVaccine(String username) throws SQLException {
        Statement stmt = databaseConnection.createStatement();

        ResultSet rs = stmt.executeQuery(String.format("select PERSONAL_INFO.vaccinationID from PERSONAL_INFO where username = '%s';", username));

        rs.next();

        if(rs.getInt(1) > 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * This method closes the database connection when this object is destroyed.
     *
     * @throws Throwable This method will throw an exception if a database access error occurs.
     */
    protected void finalize() throws Throwable {
        databaseConnection.close();
    }


    /**
     * This method is used for database testing purposes. This method should NOT be executed when trying to launch the
     * application. To launch the application, run Main.main().
     *
     * @param args These are Java's command-line arguments.
     * @throws SQLException There are several method within this class that may throw this exception.
     */
    public static void main(String[] args) throws SQLException {

        //System.out.println(new Team5DatabaseConnection().getUsersVaccine("dbodn"));

        //Date date = new Date(new GregorianCalendar(2023, 3, 23).getTimeInMillis());

        //new Team5DatabaseConnection().updateEvent(new Event("Dannys Birthday", "livenation", "Dannys house", 300, false, false, false, false, date, 4, 0));

        //new Team5DatabaseConnection().deleteEvent("Test Event 3");

        //new Team5DatabaseConnection().updateUser("dbodn", "Danny", "Bodin", "west", date);

//        Event event = new Event("Dannys Birthday", "livenation", "ic", 200, false, false, false, false, date, 3, 0);
//
//        new Team5DatabaseConnection().createNewEvent(event);
//
//        ArrayList<Event> events = new Team5DatabaseConnection().getEvents("livenation");
//
//        System.out.println(events);


        //System.out.println(new Team5DatabaseConnection().findUser("dbodn"));

        //new Team5DatabaseConnection().createNewEvent("Justin Bieber in Iowa City", "livenation", "Iowa City", "2022-04-01 18:00:00" , 10000, true, false, true, false);


//        new Team5DatabaseConnection().insertNewUser("John", "Doe", "jd", "password4");

    }

}
