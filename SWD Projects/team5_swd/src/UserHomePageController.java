import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class is responsible for controlling the user homepage.
 *
 * @author Danny Bodin, John Elwart, Tucker Dickson
 * @version 1.0
 * @since 12/3/21
 */
public class UserHomePageController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * This method is responsible for setting all of the fields on the screen to display the user's personal information.
     * It is also responsible for populating the available events list.
     *
     * @param username The username of the user.
     * @throws SQLException Thrown by Team5DatabaseConnection.getAllEvents.
     * @throws ClassNotFoundException Thrown if a class cannot be found.
     */
    public void displayUserInfo(String username) throws SQLException, ClassNotFoundException {
        // get user from database
        User user = new Team5DatabaseConnection().findUser(username);

        // get all information from user
        userNameField.setText(user.getUsername());
        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
        dateOfBirthField.setText(new Team5DatabaseConnection().getDOB(username).toString());
        password = user.getPassword();

        welcomeLabel.setText("Welcome, " + user.getFirstName() + " " + user.getLastName() + "!");

        // clear the events list and then repopulate to avoid doubling up
        availableEventsList.getItems().clear();

        events = new Team5DatabaseConnection().getAllEvents();

        for (int i = 0; i < events.size(); i++) {
            availableEventsList.getItems().add(0, events.get(i));
        }

        // populate the COVID Info tab
        loadVaccineInfo();
    }

    private String password;

    @FXML
    private Label userNameField;

    @FXML
    private Label firstNameField;

    @FXML
    private Label lastNameField;

    @FXML
    private Label dateOfBirthField;

    @FXML
    private Label successfulUpdateLabel;

    @FXML
    private ListView<Event> availableEventsList;

    @FXML
    private ListView<Event> registrationList;

    @FXML
    private TextField updateFirstNameEntryField;

    @FXML
    private TextField updateLastNameEntryField;

    @FXML
    private TextField updatePasswordEntryField;

    @FXML
    private DatePicker updateDateOfBirthEntryField;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label errorLabel1;

    @FXML
    private TextField vaccineId;

    @FXML
    private CheckBox firstDoseCheckbox;

    @FXML
    private CheckBox secondDoseCheckbox;

    @FXML
    private CheckBox boosterCheckbox;

    @FXML
    private DatePicker negativeTestDate;

    @FXML
    private Button vaccineStatusSaveButton;

    @FXML
    private Button negativeTestSaveButton;

    ArrayList<Event> events;
    ArrayList<Event> registrations;


    @FXML
    void registerButtonPressed(ActionEvent event) throws SQLException, ClassNotFoundException {
        Event selected = availableEventsList.getSelectionModel().getSelectedItem();
        registrations.add(selected);
        events.remove(selected);

        new Team5DatabaseConnection().createRegistration(userNameField.getText(), selected.getEventName());

        this.loadRegisteredEvents(userNameField.getText());
    }


    @FXML
    void updateFieldModified(MouseEvent event) {
        successfulUpdateLabel.setVisible(false);
        errorLabel1.setVisible(false);
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("signInPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void loadRegisteredEvents(String username) throws SQLException, ClassNotFoundException {
        registrations = new Team5DatabaseConnection().getUsersEvents(username);

        registrationList.getItems().clear();

        for (int i = 0; i < registrations.size(); i++) {
            registrationList.getItems().add(i, registrations.get(i));
        }
    }

    /**
     * This method is used to update the personal information of the user.
     *
     * @param event This ActionEvent indicates that the update button has been pressed.
     * @throws SQLException Thrown by various Team5DatabaseConnection methods.
     * @throws ClassNotFoundException Thrown if a class cannot be found.
     */
    @FXML
    void updateInfoButtonPressed(ActionEvent event) throws SQLException, ClassNotFoundException {
        String firstname;
        String lastname;
        String password;
        LocalDate dob;

        String username = userNameField.getText();
        boolean isValid = false;

        // only grab information from the GUI fields if they contains information
        // don't update any fields that are blank
        if(updateFirstNameEntryField.getText().equals("")) {
            firstname = firstNameField.getText();
        } else {
            firstname = updateFirstNameEntryField.getText();
        }

        if(updateLastNameEntryField.getText().equals("")) {
            lastname = lastNameField.getText();
        } else {
            lastname = updateLastNameEntryField.getText();
        }

        if(updatePasswordEntryField.getText().equals("")) {
            password = this.password;
        } else {
            // make sure the new password is legit
            password = updatePasswordEntryField.getText();

            boolean isDigit = false;
            int i = 0;

            while (!isDigit && i < password.length()) {
                if (Character.isDigit(password.charAt(i))) {
                    isDigit = true;
                }

                i++;
            }

            if (password.length() >= 4 && isDigit) {
                isValid = true;
            } else {
                errorLabel1.setVisible(true);
            }
        }

        if(updateDateOfBirthEntryField.getValue() == null) {
            dob = new Team5DatabaseConnection().getDOB(username).toLocalDate();
        } else {
            dob = updateDateOfBirthEntryField.getValue();
        }

        // update the users info in the database
        if (isValid) {
            new Team5DatabaseConnection().updateUser(username, firstname, lastname, password, Date.valueOf(dob));
            displayUserInfo(username);
            successfulUpdateLabel.setVisible(true);
        }

        // clear all entry fields
        updateFirstNameEntryField.clear();
        updateLastNameEntryField.clear();
        updatePasswordEntryField.clear();
        updateDateOfBirthEntryField.setValue(null);
    }

    @FXML
    void saveVaccineStatus(ActionEvent event) throws SQLException {
        if(new Team5DatabaseConnection().doesUserHaveVaccine(userNameField.getText())){
            new Team5DatabaseConnection().updateVaccine(new Vaccine(Integer.parseInt(vaccineId.getText()), firstDoseCheckbox.isSelected(), secondDoseCheckbox.isSelected(), boosterCheckbox.isSelected()));
        }
        else{
            new Team5DatabaseConnection().createVaccine(userNameField.getText(), new Vaccine(Integer.parseInt(vaccineId.getText()), firstDoseCheckbox.isSelected(), secondDoseCheckbox.isSelected(), boosterCheckbox.isSelected()));
        }
        this.loadVaccineInfo();
    }

    public void loadVaccineInfo() throws SQLException {
        if(new Team5DatabaseConnection().doesUserHaveVaccine(userNameField.getText())){
            Vaccine vaccine = new Team5DatabaseConnection().getUsersVaccine(userNameField.getText());
            vaccineId.setText(Integer.toString(vaccine.getVaccinationID()));
            vaccineId.setEditable(false);
            firstDoseCheckbox.setSelected(vaccine.isHasFirstDose());
            secondDoseCheckbox.setSelected(vaccine.isHasSecondDose());
            boosterCheckbox.setSelected(vaccine.isHasBooster());
        }
    }

    @FXML
    void saveNegativeTest(ActionEvent event) throws SQLException {
        new Team5DatabaseConnection().updateNegativeTest(userNameField.getText(), Date.valueOf(negativeTestDate.getValue()));
    }

    @FXML
    void selectedRegisteredItem(MouseEvent event) {
        availableEventsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int index;
                try {
                    // load the root with EventDetail.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("EventDetail.fxml"));
                    root = loader.load();

                    EventDetailController controller = loader.getController();

                    // get the index of the selected listview item
                    if (mouseEvent.getSource() == availableEventsList) {
                        index = (events.size() - 1) - availableEventsList.getSelectionModel().getSelectedIndex();

                        controller.setOrganizerUsername(userNameField.getText());
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

                    }

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

        registrationList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            /**
             * This method is responsible for switching the current scene from the organizer home page to the event detail page.
             * It is also responsible for passing in event information into the next scene so that it can be populated on the
             * screen.
             *
             * @param mouseEvent This MouseEvent represents the clicking of an event in the events listview.
             */
            @Override
            public void handle(MouseEvent mouseEvent) {
                int index;
                try {
                    // load the root with EventDetail.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("EventDetail.fxml"));
                    root = loader.load();

                    EventDetailController controller = loader.getController();

                    // get the index of the selected listview item
                    if (mouseEvent.getSource() == registrationList){
                        index = (registrations.size() - 1) - registrationList.getSelectionModel().getSelectedIndex();

                        controller.setOrganizerUsername(userNameField.getText());
                        controller.setEventName(registrations.get(index).getEventName());
                        controller.setEventLocation(registrations.get(index).getLocation());
                        controller.setEventDate(registrations.get(index).getDate());
                        controller.setCapacity(registrations.get(index).getCapacity());

                        controller.setMaskState(registrations.get(index).isNeedsMask());
                        controller.setVaccineState(registrations.get(index).isNeedsVaccine());
                        controller.setTestState(registrations.get(index).isTestReplacement());
                        controller.setBoosterState(registrations.get(index).isNeedsBooster());

                        controller.setHours(registrations.get(index).getHours());
                        controller.setMinutes(registrations.get(index).getMinutes());
                    }

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
}

