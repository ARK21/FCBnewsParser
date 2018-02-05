import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class start application GUI
 */
public class FCB extends Application {

    /**
     * Method start app
     * @param args input parameters
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method show gui
     * @param primaryStage main stage
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // get resources for Parent
            Parent root = FXMLLoader.load(getClass().getResource("/META-INF/view/gui.fxml"));
            // set title, set Parent
            primaryStage.setTitle("News");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            // show Gui
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
