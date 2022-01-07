import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class is responsible for loading the initial page and starting the the application. This class contains two
 * methods, start() and main(). The main() method must be run in order to launch the application. This class
 * extends the Application class, which defines the abstract start() method.
 *
 * @author Danny Bodin, John Elwart, Tucker Dickson
 * @version 1.0
 * @since 12/3/21
 */
public class Main extends Application {
    /**
     * This method is responsible for defining a new Parent object, loading the fxml file for the initial page into that
     * Parent, using the Parent to define a new Scene object, and then using that Scene object to set the stage and display
     * the initial page (the sign in page) of the application.
     *
     * @param stage This Stage object represents the primary stage of the application, on which the sign-in page scene
     *              will laid.
     * @throws Exception This method will throw an IOException if the fxml file provided can't be located.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // locate and load the root fxml file (signInPage.fxml)
        Parent root = FXMLLoader.load(getClass().getResource("signInPage.fxml"));

        // use the root (fxml file) to define a new Scene
        Scene scene = new Scene(root);

        // lay the scene onto the stage
        stage.setScene(scene);

        // display the stage
        stage.setTitle("Team 5 group project");
        stage.show();
    }

    /**
     * This method is the primary executable for this project. This method needs to be run in order to launch the program.
     * It takes in one argument and passes that argument to the start() method, which then sets up and displays the first
     * GUI scene.
     *
     * @param args These are Java's command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
