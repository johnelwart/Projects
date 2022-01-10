import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BaseChangeTest extends Application {

    /**
     * Launches the application built in the start method.
     * @param args Default parameter.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Loads the javaFX .fxml file with the GUI properties built in SceneBuilder and configures the window.
     * @param stage Determines the state of the window.
     * @throws Exception Checked exception.
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BaseChange.fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("Base Change");
        stage.setScene(scene);
        stage.show();
    }
}
