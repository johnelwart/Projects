import java.sql.Date;

/**
 * This class is a template for Event objects, which represent events organized by event organizers. Examples of events
 * might include concerts, sporting events, movies, parties, etc. An event organizer can create and modify events from
 * their homepage, and individuals can register for events from their homepage. Each Event object contains attributes
 * representing the event name, time, location, capacity, organizer, and safety requirements.
 *
 * @author Danny Bodin, John Elwart, Tucker Dickson
 * @version 1.0
 * @since 12/3/21
 */
public class Event {
    /**
     * This private String represents the name of the event (ie. Danny's birthday party)
     */
    private String eventName;

    /**
     * This private String represents the username of the organizer of the event (ie. dbodn)
     */
    private String organizerUsername;

    /**
     * This private String represents the location of the event.
     */
    private String location;

    /**
     * This private int represents the maximum capacity of the event.
     */
    private int capacity;

    /**
     * This private boolean represents whether or not an individual needs to have their initial vaccines (booster not
     * included) to attend the event.
     */
    private boolean needsVaccine;

    /**
     * This private boolean represents whether or not an individual needs to have their booster vaccine to attend the
     * event.
     */
    private boolean needsBooster;

    /**
     * This private boolean represents whether or not an individual can replace the vaccine/booster requirements with a
     * negative covid test. If true, an individual will not need to be vaccinated or have a booster if they have recently
     * tested negative.
     */
    private boolean testReplacement;

    /**
     * This private boolean represents whether or not an individual needs to wear a mask to the event.
     */
    private boolean needsMask;

    /**
     * This private Date object represents the date that the event is taking place on.
     */
    private Date date;

    /**
     * This private int represents the hour that the event will take place at (for example: if the event time is 1:30,
     * hours will be 1).
     */
    private int hours;

    /**
     * This private int represents the minutes that the event will take place at (for example: if the event time is
     * 1:30, minutes will be 30).
     */
    private int minutes;

    private String amOrPM;

    /**
     * This is the no-argument constructor for the event class. This constructor simple initializes eventName, capacity,
     * and location to "NoName", 0, and "NoLocal", respectively.
     */
    public Event () {
        eventName = "NoName";
        capacity = 0;
        location = "NoLocal";
    }

    /**
     * This is the other of the two constructors for the Event class. This constructor takes in one parameter for each
     * of the private member variables and uses them to initialize each one.
     *
     * @param eventName This String represents the name of the event.
     * @param organizerUsername This String represents the name of the organizer of the event.
     * @param location This String represents the location of the event.
     * @param capacity This int represents the maximum capacity of the event.
     * @param needsVaccine This boolean indicates whether a vaccine is required for an individual to attend this event.
     * @param needsBooster This boolean indicates whether a booster is required for an individual to attend this event.
     * @param testReplacement This boolean indicates whether an individual can "replace" a lack of vaccination/booster
     *                        with a negative covid test.
     * @param needsMask This boolean indicates whether a mask is required at this event.
     * @param date This Date object represents the date of the event.
     * @param hours This int represents the hour that the event will take place at.
     * @param minutes This int represents the minute that the event will take place at.
     */
    public Event(String eventName, String organizerUsername, String location, int capacity, boolean needsVaccine, boolean needsBooster, boolean testReplacement, boolean needsMask, Date date, int hours, int minutes) {
        this.eventName = eventName;
        this.organizerUsername = organizerUsername;
        this.location = location;
        this.capacity = capacity;
        this.needsVaccine = needsVaccine;
        this.needsBooster = needsBooster;
        this.testReplacement = testReplacement;
        this.needsMask = needsMask;
        this.date = date;
        this.hours = hours;
        this.minutes = minutes;

        if (hours >= 12) {
            amOrPM = "PM";
        } else {
            amOrPM = "AM";
        }
    }

    /**
     * This method returns a String representation of of an Event object. The String will assume the following format:
     * "eventName, location, date, hours:minutes".
     *
     * @return This method returns a String representation of an Event object.
     */
    public String toString() {
        int retrievedHours = hours;
        int returnHours;

        if (retrievedHours > 12) {
            returnHours = retrievedHours - 12;
        } else if (retrievedHours == 12) {
            returnHours = 12;
        } else if (retrievedHours == 0) {
            returnHours = 12;
        } else {
            returnHours = retrievedHours;
        }

        return eventName + ", " + location + ", " + date.toString() + ", " + returnHours + ":" + String.format("%02d", minutes) + " " + amOrPM;
    }

    /**
     * This is the getter method for the eventName attribute.
     *
     * @return This method returns a String containing the name of the event.
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * This is the getter for the organizerUsername attribute.
     *
     * @return This method returns a String containing the name of the organizer of the event.
     */
    public String getOrganizerUsername() {
        return organizerUsername;
    }

    /**
     * This is the getter for the location attribute.
     *
     * @return This method returns a String containing the location of the event.
     */
    public String getLocation() {
        return location;
    }

    /**
     * This is the getter for the capacity attribute.
     *
     * @return This method returns an int representing the maximum capacity of the event.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * This is the getter for the needsVaccine attribute.
     *
     * @return This method returns a boolean value indicating whether an individual needs to be vaccinated to attend.
     */
    public boolean isNeedsVaccine() {
        return needsVaccine;
    }

    /**
     * This is the getter for the needsBooster attribute.
     *
     * @return This method returns a boolean value indicating whether an individual needs to have a booster shot to attend.
     */
    public boolean isNeedsBooster() {
        return needsBooster;
    }

    /**
     * This is the getter for the needsVaccine attribute.
     *
     * @return This method returns a boolean value indicating whether an individual can replace a lack of vaccination/booster
     * with a negative covid test.
     */
    public boolean isTestReplacement() {
        return testReplacement;
    }

    /**
     * This is the getter for the needsMask attribute.
     *
     * @return This method returns a boolean value indicating whether an individual needs to wear a mask to attend.
     */
    public boolean isNeedsMask() {
        return needsMask;
    }

    /**
     * This is the getter for the date attribute.
     *
     * @return This method returns a Date object representing the date of the event.
     */
    public Date getDate() {
        return date;
    }

    /**
     * This is the getter for the hours attribute.
     *
     * @return This method returns an int representing the hour that the event will take place at.
     */
    public int getHours() {
        return hours;
    }

    /**
     * This is the getter for the minutes attribute.
     *
     * @return This method returns an int representing the minutes that the event will take place at.
     */
    public int getMinutes() {
        return minutes;
    }

}
