import db.SqlManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.BarcaSiteParser;

import java.io.IOException;

/**
 * This class start application GUI
 */
public class FCB extends Application {
    BarcaSiteParser parser;

    /**
     * Method start app
     *
     * @param args input parameters
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method show gui
     *
     * @param primaryStage main stage
     */
    @Override
    public void start(Stage primaryStage) {
        // update data
        updateData();
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

    private void updateData() {
        SqlManager manager = new SqlManager();
        Thread updateThread = new Thread(() -> {
            while (true) {
                parser = new BarcaSiteParser(1);
                try {
                    if (manager.add(parser.getData())) {
                        parser.clear();
                    }
                    Thread.sleep(1000 * 60);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        updateThread.setDaemon(true);
        updateThread.start();
    }
}
