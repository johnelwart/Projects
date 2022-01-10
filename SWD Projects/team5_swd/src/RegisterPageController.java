import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class RegisterPageController {

    Stage stage;
    Scene scene;
    Parent root;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label dateOfBirthLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label orgNameLabel;

    @FXML
    private TextField firstNameEntryField;

    @FXML
    private TextField lastNameEntryField;

    @FXML
    private TextField usernameEntryField;

    @FXML
    private TextField passwordEntryField;

    @FXML
    private DatePicker dateOfBirthEntryField;

    @FXML
    private CheckBox orgCheck;

    @FXML
    private Text errorText;

    @FXML
    private Label instructionsText;

    @FXML
    private Label errorLabel;

    @FXML
    private Button backButton;

    @FXML
    void orgCheckBox(ActionEvent event) {
        // if the org checkbox is selected, do the following...
        if(orgCheck.isSelected()) {
            // set the first name label invisible and the org name label visible
            firstNameLabel.setVisible(false);
            orgNameLabel.setVisible(true);

            // grey out the last name label and date of birth label
            lastNameLabel.setTextFill(Color.GREY);
            dateOfBirthLabel.setTextFill(Color.GREY);

            // disable the last name entry field and the date of birth entry field
            lastNameEntryField.setDisable(true);
            dateOfBirthEntryField.setDisable(true);
        }
        // if the org checkbox is unchecked, do the following
        else {
            // set the first name label visible and the org name label invisible
            firstNameLabel.setVisible(true);
            orgNameLabel.setVisible(false);

            // make the last name label and date of birth label black again (like the rest of the labels)
            lastNameLabel.setTextFill(Color.BLACK);
            dateOfBirthLabel.setTextFill(Color.BLACK);

            // enable the last name entry field and the date of birth entry field
            lastNameEntryField.setDisable(false);
            dateOfBirthEntryField.setDisable(false);
        }
    }

    @FXML
    void registerButton1Pushed(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        // get the information from the fields required by both the individual and organization registration pages
        String firstName = firstNameEntryField.getText();
        String userName = usernameEntryField.getText();
        String password = passwordEntryField.getText();
        boolean isDigit = false;
        int i = 0;

        while (!isDigit && i < password.length()) {
            if(Character.isDigit(password.charAt(i))) {
                isDigit = true;
            }

            i++;
        }

        // if the entity registering is an organization
        if (password.length() >= 4 && isDigit) {
            errorLabel.setVisible(false);
            if (orgCheck.isSelected()) {
                if (firstNameEntryField.getText().equals("") || usernameEntryField.getText().equals("") || passwordEntryField.getText().equals("")) {
                    errorText.setVisible(true);
                } else {
                    // create a new user object in database
                    new Team5DatabaseConnection().createNewUser(firstName, null, userName, password, true, null);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("OrgHomepage.fxml"));
                    root = loader.load();

                    OrgHomepageController controller = loader.getController();
                    controller.setOrganizerUsername(usernameEntryField.getText());
                    controller.displayEventInfo();

                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
            // if the entity registering is an individual
            else {
                if (firstNameEntryField.getText().equals("") || usernameEntryField.getText().equals("") || passwordEntryField.getText().equals("") || lastNameEntryField.getText().equals("")) {
                    errorText.setVisible(true);
                } else {
                    // get the last name and date of birth
                    String lastName = lastNameEntryField.getText();
                    Date dateOfBirth = Date.valueOf(dateOfBirthEntryField.getValue());

                    // create a new user object in database
                    new Team5DatabaseConnection().createNewUser(firstName, lastName, userName, password, false, dateOfBirth);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("userHomePage.fxml"));
                    root = loader.load();

                    UserHomePageController homePageController = loader.getController();
                    homePageController.displayUserInfo(usernameEntryField.getText());
                    homePageController.loadRegisteredEvents(usernameEntryField.getText());

                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        } else {
            errorLabel.setVisible(true);
        }
    }

    @FXML
    void backButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("signInPage.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
