import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * This class is used to control the organization homepage (the page that an organization is redirected to after login).
 *
 * @author Danny Bodin, John Elwart, Tucker Dickson
 * @version 1.0
 * @since 12/3/21
 */
public class OrgHomepageController {
    /**
     * This private Stage object is used as a spot to lay the above scene.
     */
    private Stage stage;

    /**
     * This private Scene object is used to display the root fxml file.
     */
    private Scene scene;

    /**
     * This private Parent object is used to load the next scene (either the register scene, user home page, or org
     * home page) whenever the corresponding button is clicked on.
     */
    private Parent root;

    /**
     * This private String holds the username of the organizer of an event
     */
    private String organizerUsername;

    /**
     * This boolean is used to store the value of the booster checkbox
     */
    private boolean isBooster = false;

    /**
     * This boolean is used to store the value of the mask checkbox
     */
    private boolean isMask = false;

    /**
     * This boolean is used to store the value of the vaccinated checkbox.
     */
    private boolean isVax = false;

    /**
     * This boolean is used to store the value of the test checkbox.
     */
    private boolean isTest = false;


    /**
     * This ArrayList is used to store the events organized by a specific organizer.
     */
    private ArrayList<Event> events;

    /**
     * This Label displays a welcome message for the user
     */
    @FXML
    private Label welcomeLabel;

    /**
     * This ListView displays all of the events for the organization.
     */
    @FXML
    private ListView<String> eventList;

    /**
     * This TextField is where the user will type the name of a new event.
     */
    @FXML
    private TextField nameField;

    /**
     * This TextField is where the user will type the location of a new event.
     */
    @FXML
    private TextField locationField;

    /**
     * This TextField is where the user will type the capacity of a new event.
     */
    @FXML
    private TextField capField;

    /**
     * This CheckBox indicates whether a mask is required for a new event.
     */
    @FXML
    private CheckBox maskCheck;

    /**
     * This CheckBox indicates whether a negative covid test can replace a lack of vaccination or booster.
     */
    @FXML
    private CheckBox negTestCheck;

    /**
     * This CheckBox indicates whether a vaccine is required for a new event.
     */
    @FXML
    private CheckBox vaxCheck;

    /**
     * This Button will be used to add a new event to the events listview.
     */
    @FXML
    private Button addButton;

    /**
     * This CheckBox indicates whether a booster is required for a new event.
     */
    @FXML
    private CheckBox boosterCheck;

    /**
     * This DatePicker will be used to set the date of a new event.
     */
    @FXML
    private DatePicker datePicker;

    /**
     * This Spinner will be used to set the time (hours) of a new event.
     */
    @FXML
    private Spinner<Integer> hoursField;

    /**
     * This Spinner will be used to set the time (minutes) of a new event.
     */
    @FXML
    private Spinner<Integer> minutesField;

    /**
     * This Label will be used to indicate an error when adding a new event (not all fields filled out).
     */
    @FXML
    private Label errorLabel;

    /**
     * This Label will be used to indicate an error when adding a new event (capacity field doesn't have an int).
     */
    @FXML
    private Label intErrorLabel;

    /**
     * This MenuButton will be used to indicate whether the event will take place in the AM or PM of the day.
     */
    @FXML
    private MenuButton ampmField;

    /**
     * This MenuItem is used to trigger the amSelected method.
     */
    @FXML
    private MenuItem amMenuItem;

    /**
     * This MenuItem is used to trigger the pmSelected method.
     */
    @FXML
    private MenuItem pmMenuItem;

    /**
     * This Text will be displayed whenever a new event is successfully added.
     */
    @FXML
    private Text successMessage;

    /**
     * This method is used to display the information for each of the organizers events in the event listview.
     *
     * @throws SQLException This exception is thrown by the findUser() and getEvents() methods.
     * @throws ClassNotFoundException This exception is thrown if a class can't be found.
     */
    public void displayEventInfo() throws SQLException, ClassNotFoundException {
        // call the findUser() method to get a user object representing the organization
        User user = new Team5DatabaseConnection().findUser(organizerUsername);

        // set the welcomeLabel text
        welcomeLabel.setText("Welcome, " + user.getFirstName() + "!");

        // make the success message invisible
        successMessage.setVisible(false);

        // call the getEvents method to get the events organized by this organization
        events = new Team5DatabaseConnection().getEvents(organizerUsername);

        // iterate over the events ArrayList, adding each to the eventList listview
        for (int i = 0; i < events.size(); i++) {
            eventList.getItems().add(0, events.get(i).toString());
        }
    }

    /**
     * This method is used to add a new event to the events listview. This method is called whenever an organization
     * presses the "Add event" button from the organization's home page.
     *
     * @param event This ActionEvent represents the pressing of the "Add event" button.
     * @throws SQLException This exception is thrown by the createNewEvent() method in Team5DatabaseConnection class.
     * @throws ClassNotFoundException This exception is thrown if a class can't be found.
     */
    @FXML
    void addBtnPressed(ActionEvent event) throws SQLException, ClassNotFoundException {
        // if the name field, location field, and capacity field are all filled out, continue
        if (!nameField.getText().equals("") && !locationField.getText().equals("") && !capField.getText().equals("")) {
            // make the error labels invisible
            intErrorLabel.setVisible(false);
            errorLabel.setVisible(false);

            // get the name, location, hours, and minutes from their corresponding fields
            String eventName = nameField.getText();
            String location = locationField.getText();
            Integer hours = hoursField.getValue();
            Integer minutes = minutesField.getValue();
            String ampm = ampmField.getText();
            Integer hoursTo24 = 0;

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

            // try to read in an int from the capacity field and create a new event with all of the information we've collected
            try {
                Integer.parseInt(capField.getText());
                String capacity = capField.getText();

            // this exception will be thrown if the capacity field contains something other than an int
            // make the capacity error label visible
                new Team5DatabaseConnection().createNewEvent(new Event(eventName, organizerUsername, location, Integer.parseInt(capacity), isVax, isBooster, isTest, isMask, new Date(new GregorianCalendar(datePicker.getValue().getYear(), datePicker.getValue().getMonthValue(), datePicker.getValue().getDayOfMonth()).getTimeInMillis()), hoursTo24, minutes));
            } catch (NumberFormatException e) {
                intErrorLabel.setVisible(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            // clear data from all fields
            nameField.setText("");
            locationField.setText("");
            datePicker.getEditor().clear();
            capField.setText("");

            // uncheck all checkboxes
            vaxCheck.setSelected(false);
            boosterCheck.setSelected(false);
            maskCheck.setSelected(false);
            negTestCheck.setSelected(false);

            // display success message
            successMessage.setVisible(true);

            // clear event list and call displayEventInfo() again to avoid doubling-up
            eventList.getItems().clear();
            displayEventInfo();

        // the following else gets executed if one or more entry fields are not filled in
        } else {
            // make the success message invisible
            successMessage.setVisible(false);

            // make the capacity error label invisible
            intErrorLabel.setVisible(false);

            // make the fields error label visible
            errorLabel.setVisible(true);
        }
    }

    /**
     * This method is used to set the text of the ampm menu list. This method gets called whenever the am menu item is
     * selected.
     *
     * @param event This ActionEvent represents the pressing of the am menu item.
     */
    @FXML
    void amSelected(ActionEvent event) {
        ampmField.setText("AM");
    }

    /**
     * This method is used to set the text of the ampm menu list. This method gets called whenever the pm menu item is
     * selected.
     *
     * @param event This ActionEvent represents the pressing of the pm menu item.
     */
    @FXML
    void pmSelected(ActionEvent event) {
        ampmField.setText("PM");
    }

    /**
     * This method is used to switch the current scene from the org home page scene back to the sign in page scene. This
     * method is called when the "Log out" button is pressed from the org home page.
     *
     * @param event This ActionEvent represents the pressing of the "Log out" button.
     * @throws IOException This IOException is thrown by the load method if there's an error when loading the fxml file.
     */
    @FXML
    void logOutButtonPressed(ActionEvent event) throws IOException {
        // load the root with signInPage.fxml
        root = FXMLLoader.load(getClass().getResource("signInPage.fxml"));

        // instantiate scene, use it to set the stage
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        // display the stage
        stage.show();
    }

    /**
     * This method is used to manipulate isMask. It is called whenever the user selects the mask checkbox.
     *
     * @param event This ActionEvent represents the clicking of the mask checkbox.
     */
    @FXML
    void maskIsChecked(ActionEvent event) {
        // if the mask checkbox is selected, set isMask to true
        if (maskCheck.isSelected()) {
            isMask = true;
        }
    }

    /**
     * This method is responsible for switching the current scene from the organizer home page to the event detail page.
     * It is also responsible for passing in event information into the next scene so that it can be populated on the
     * screen.
     *
     * @param event This MouseEvent represents the clicking of an event in the events listview.
     */
    @FXML
    void selectedItem(MouseEvent event) {
        eventList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            /**
             * This method is responsible for switching the current scene from the organizer home page to the event detail page.
             * It is also responsible for passing in event information into the next scene so that it can be populated on the
             * screen.
             *
             * @param mouseEvent This MouseEvent represents the clicking of an event in the events listview.
             */
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    // load the root with EventDetail.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("EventDetail.fxml"));
                    root = loader.load();

                    // get the index of the selected listview item
                    int index = (events.size() - 1) - eventList.getSelectionModel().getSelectedIndex();

                    // instantiate a new EventDetailController
                    EventDetailController controller = loader.getController();

                    // pass all relavent information to the new EventDetailController
                    controller.setOrganizerUsername(organizerUsername);
                    controller.setEventName(events.get(index).getEventName());
                    controller.setEventLocation(events.get(index).getLocation());
                    controller.setEventDate(events.get(index).getDate());
                    controller.setCapacity(events.get(index).getCapacity());

                    controller.setMaskState(events.get(index).isNeedsMask());
                    controller.setVaccineState(events.get(index).isNeedsVaccine());
                    controller.setTestState(events.get(index).isTestReplacement());
                    controller.setBoosterState(events.get(index).isNeedsBooster());

                    controller.setHours(events.get(index).getHours());
                    controller.setMinutes(events.get(index).getMinutes());

                    // call EventDetailController.displayEventInfo to display the information
                    controller.displayEventInfo();

                    // instantiate scene; set stage and display
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException | SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * This method is used to manipulate isVax. It is called whenever the user selects the vax checkbox.
     *
     * @param event This ActionEvent represents the clicking of the vax checkbox.
     */
    @FXML
    void vaxIsChecked(ActionEvent event) {
        // if the vax checkbox is selected, set isVax to true
        if (vaxCheck.isSelected()) {
            isVax = true;
        }
    }

    /**
     * This method is used to manipulate isTest. It is called whenever the user selects the test checkbox.
     *
     * @param event This ActionEvent represents the clicking of the test checkbox.
     */
    @FXML
    void negTestIsChecked(ActionEvent event) {
        // if the test checkbox is selected, set isTest to true
        if (negTestCheck.isSelected()) {
            isTest = true;
        }
    }

    /**
     * This method is used to manipulate isBooster. It is called whenever the user selects the booster checkbox.
     *
     * @param event This ActionEvent represents the clicking of the booster checkbox.
     */
    @FXML
    void BoosterIsChecked(ActionEvent event) {
        // if the booster checkbox is selected, set isBooster to true
        if (boosterCheck.isSelected()) {
            isBooster = true;
        }
    }

    /**
     * This is the setter for the username of the organizer.
     *
     * @param organizerUsername This String will be used to initialize organizerUsername.
     */
    public void setOrganizerUsername(String organizerUsername) {
        this.organizerUsername = organizerUsername;
    }
}
