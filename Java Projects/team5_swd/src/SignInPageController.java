import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * This class controls the sign in scene, which is the first scene that the user encounters when the application is
 * launched.
 *
 * @author Danny Bodin, John Elwart, Tucker Dickson
 */
public class SignInPageController {
    /**
     * This private Parent object is used to load the next scene (either the register scene, user home page, or org
     * home page) whenever the corresponding button is clicked on.
     */
    private Parent root;

    /**
     * This private Scene object is used to display the root fxml file.
     */
    private Scene scene;

    /**
     * This private Stage object is used as a spot to lay the above scene.
     */
    private Stage stage;

    /**
     * This private PasswordField is where the user will type in their password when they are trying to log in.
     */
    @FXML
    private PasswordField passwordEntryField;

    /**
     * This private TextField is where the user will type in their username when they are trying to log in.
     */
    @FXML
    private TextField usernameEntryField;

    /**
     * This private Text object contains a message that notifies the user if the login information is invalid (or if
     * there wasn't any login information entered).
     */
    @FXML
    private Text errorText;

    /**
     * This method is used to switch the current scene from the sign in page to the register page. This method is called
     * when the user clicks on the "Register" button on the sign in page.
     *
     * @param event This ActionEvent represents the user clicking on the "Register" button on the sign in page.
     * @throws IOException The load method will throw an IOException if it is unable to load the scene passed into it.
     */
    @FXML
    void registerButtonPushed(ActionEvent event) throws IOException {
        // load registerPage.fxml into the root
        root = FXMLLoader.load(getClass().getResource("registerPage.fxml"));

        // use root to initialize Scene and then use scene to set the stage
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        // display the stage on the screen
        stage.show();
    }

    /**
     * This method is used to switch the current scene from the sign in page to either the individual home page or the
     * organization home page, depending on what type of account the user is. This method is called when the user clicks
     * on the "Sign in" button on the sign in page.
     *
     * @param event This ActionEvent represents the user clicking on the "Sign in" button on the sign in page.
     */
    @FXML
    void signInButtonPushed(ActionEvent event) {

        String username = usernameEntryField.getText(); // get the username from the username entry field
        String password = passwordEntryField.getText(); // get the password from the password entry field

        try{
            // if the username and password are correct, proceed
            if(new Team5DatabaseConnection().verifyUser(username, password)){
                // set the visibility of the error label to false (in case we return to this screen)
                errorText.setVisible(false);

                // if the username entered corresponds to an organization, do the following
                if(new Team5DatabaseConnection().isOrganization(usernameEntryField.getText())){
                    // load root with OrgHomepage.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("OrgHomepage.fxml"));
                    root = loader.load();

                    // instantiate a new OrgHomepageController object
                    OrgHomepageController controller = loader.getController();

                    // call OrgHomepageController.setOranizerUsername() to "pass" the username to the OrgHomepageController
                    controller.setOrganizerUsername(usernameEntryField.getText());

                    // call OrgHomepageController.displayEventInfo() to populate the information fields in the org homepage
                    controller.displayEventInfo();

                    // instantiate the scene, set the stage and display
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                // otherwise, if the username entered corresponds to an individual, do the following
                else{
                    // load root with userHomepage.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("userHomePage.fxml"));
                    root = loader.load();

                    // instantiate a new UserHomePageController object
                    UserHomePageController homePageController = loader.getController();

                    // call UserHomePageController.displayUserInfo() to populate the personal information fields in the user homepage
                    homePageController.displayUserInfo(username);

                    // call UserHomePageController.loadRegisteredEvents() to populate the registered events listview
                    homePageController.loadRegisteredEvents(username);

                    // instantiate scene, set the stage and display
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
            // if the username and/or password are not correct, display the error label
            else{
                errorText.setVisible(true);
            }
        // if the username field is empty, an exception will be thrown
        // catch it and display the error label
        } catch (ClassNotFoundException | IOException | SQLException throwables) {
            errorText.setVisible(true);
        }


    }
}
