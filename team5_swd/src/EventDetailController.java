import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * This class is responsible for controlling the event detail page.
 *
 * @author Danny Bodin, John Elwart, Tucker Dickson
 * @version 1.0
 * @since 12/3/21
 */
public class EventDetailController {
    
    private Stage stage;
    private Scene scene;
    private Parent root;

    private String organizerUsername;
    private String eventName;
    private String eventLocation;
    private Date eventDate;
    private int capacity;
    private boolean maskState;
    private boolean vaccineState;
    private boolean testState;
    private boolean boosterState;
    private int hours;
    private int updateHours;
    private int minutes;

    private ArrayList<User> attendees;

    public LocalDate LocalDate (String dateString) {
        LocalDate date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        date = LocalDate.parse(dateString, formatter);

        return date;
    }

    /**
     * This method is responsible for displaying information in the various GUI components on the event detail page.
     * It is also responsible for populating the attendee list on the right side of the screen.
     *
     * @throws SQLException Team5DatabaseConnection.getAttendeesForEvent() throws this exception.
     * @throws ClassNotFoundException This exception gets thrown if a class cannot be found.
     */
    public void displayEventInfo() throws SQLException, ClassNotFoundException {
        nameField.setText(eventName);
        locationField.setText(eventLocation);
        dateSelection.setValue(LocalDate(eventDate.toString()));
        capacityField.setText(String.valueOf(capacity));

        maskCheck.setSelected(maskState);
        vaxCheck.setSelected(vaccineState);
        testCheck.setSelected(testState);
        boosterCheck.setSelected(boosterState);

        if (hours == 12) {
            updateHours = 12;
            ampmSelection.setText("PM");
        } else if (hours == 0) {
            updateHours = 12;
            ampmSelection.setText("AM");
        } else if (hours > 12) {
            updateHours = hours - 12;
            ampmSelection.setText("PM");
        } else {
            updateHours = hours;
            ampmSelection.setText("AM");
        }
        
        hoursField.getValueFactory().setValue(updateHours);
        minutesField.getValueFactory().setValue(minutes);

        // populate attendees listview by calling Team5DatabaseConnection.getAttendeesForEvent()
        attendees = new Team5DatabaseConnection().getAttendeesForEvent(eventName);
        for (int i = 0; i < attendees.size(); i++) {
            attendeesList.getItems().add(0, attendees.get(i).toString());
        }

        if (!new Team5DatabaseConnection().isOrganization(organizerUsername)) {
            nameField.setEditable(false);
            locationField.setEditable(false);
            dateSelection.setEditable(false);
            hoursField.setEditable(false);
            minutesField.setEditable(false);
            ampmSelection.setDisable(true);
            capacityField.setEditable(false);
            maskCheck.setDisable(true);
            vaxCheck.setDisable(true);
            testCheck.setDisable(true);
            boosterCheck.setDisable(true);

            saveButton.setVisible(false);
            deleteButton.setVisible(false);
            backbutton.setVisible(true);
        }
    }

    @FXML
    void backButtonPressed(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userHomePage.fxml"));
        root = loader.load();

        UserHomePageController homePageController = loader.getController();

        // call UserHomePageController.displayUserInfo() to populate the personal information fields in the user homepage
        homePageController.displayUserInfo(organizerUsername);

        // call UserHomePageController.loadRegisteredEvents() to populate the registered events listview
        homePageController.loadRegisteredEvents(organizerUsername);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private Button deleteButton;

    @FXML
    private Button backbutton;


    @FXML
    private Label detailsLabel;

    @FXML
    private Label attendeesLabel;

    @FXML
    private ListView<String> attendeesList;

    @FXML
    private TextField nameField;

    @FXML
    private TextField capacityField;

    @FXML
    private TextField locationField;

    @FXML
    private Button saveButton;

    @FXML
    private Label NameLabel;

    @FXML
    private Label CapacityLabel;

    @FXML
    private Label TimeLabel;

    @FXML
    private Label LocationLabel;

    @FXML
    private Label PrecautionLabel;

    @FXML
    private Spinner<Integer> hoursField;

    @FXML
    private Spinner<Integer> minutesField;

    @FXML
    private Label colonLabel;

    @FXML
    private MenuButton ampmSelection;

    @FXML
    private DatePicker dateSelection;

    @FXML
    private Label dateLabel;

    @FXML
    private CheckBox maskCheck;

    @FXML
    private CheckBox boosterCheck;

    @FXML
    private CheckBox testCheck;

    @FXML
    private CheckBox vaxCheck;

    @FXML
    void amSelected(ActionEvent event) {
        ampmSelection.setText("AM");
    }

    @FXML
    void boosterIsChecked(ActionEvent event) {

    }

    @FXML
    void maskIsChecked(ActionEvent event) {

    }

    @FXML
    void pmSelected(ActionEvent event) {
        ampmSelection.setText("PM");
    }

    /**
     * This method is responsible for saving the information that the user entered and then sending the user back to the
     * organization homepage. This method is called whenever the save button is pressed.
     *
     * @param event This ActionEvent indicates that the save button was pressed.
     * @throws IOException Thrown by FXMLLoader.load()
     * @throws SQLException Thrown by Team5DatabaseConnection.updateEvent().
     * @throws ClassNotFoundException Thrown when a class cannot be found.
     */
    @FXML
    void saveButtonPressed(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

        String eventLocation;
        LocalDate eventDate;
        int hour, minutes;
        int capacity;
        boolean isMaskChecked;
        boolean isVaxChecked;
        boolean isTestChecked;
        boolean isBoosterChecked;
        Integer hoursTo24 = 0;

        // get information from GUI fields
        String ampm = ampmSelection.getText();
        String eventName = nameField.getText();
        eventLocation = locationField.getText();
        eventDate = dateSelection.getValue();
        hours = hoursField.getValue();

        if (ampm.equals("AM")) {
            if (hours == 12) {
                hoursTo24 = 0;
            } else {
                hoursTo24 = hours;
            }
        } else if (ampm.equals("PM")) {
            if (hours == 12) {
                hoursTo24 = 12;
            } else {
                hoursTo24 = hours + 12;
            }
        }

        // get more information from fields
        minutes = minutesField.getValue();
        capacity = Integer.parseInt(capacityField.getText());
        isMaskChecked = maskCheck.isSelected();
        isVaxChecked = vaxCheck.isSelected();
        isTestChecked = testCheck.isSelected();
        isBoosterChecked = boosterCheck.isSelected();

        // update the event
        new Team5DatabaseConnection().updateEvent(new Event(eventName, organizerUsername, eventLocation, capacity, isVaxChecked, isBoosterChecked, isTestChecked, isMaskChecked, Date.valueOf(eventDate), hoursTo24, minutes));

        // change the scene to OrgHomepage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OrgHomepage.fxml"));
        root = loader.load();
    
        OrgHomepageController controller = loader.getController();
        controller.setOrganizerUsername(organizerUsername);
        controller.displayEventInfo();
    
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method is responsible for deleting an event from the database. This method is called when the delete event
     * button is pushed.
     *
     * @param event This ActionEvent indicates that the delete button has been pressed.
     * @throws SQLException This exception is thrown by Team5DatabaseConnection.deleteEvent().
     * @throws ClassNotFoundException This exception is thrown when a class cannot be found.
     * @throws IOException This method is thrown by FXMLLoader.load().
     */
    @FXML
    void deleteEventButtonPressed(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        new Team5DatabaseConnection().deleteEvent(nameField.getText());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("OrgHomepage.fxml"));
        root = loader.load();

        OrgHomepageController controller = loader.getController();
        controller.setOrganizerUsername(organizerUsername);
        controller.displayEventInfo();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void testIsChecked(ActionEvent event) {

    }

    @FXML
    void vaxIsChecked(ActionEvent event) {

    }

    public void setOrganizerUsername(String organizerUsername) {
        this.organizerUsername = organizerUsername;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setMaskState(boolean maskState) {
        this.maskState = maskState;
    }

    public void setVaccineState(boolean vaccineState) {
        this.vaccineState = vaccineState;
    }

    public void setTestState(boolean testState) {
        this.testState = testState;
    }

    public void setBoosterState(boolean boosterState) {
        this.boosterState = boosterState;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
