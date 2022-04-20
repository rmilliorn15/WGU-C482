package main;

/**
 * @author Richard Milliorn
 * WGU C482
 */

import controller.MainPage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * override for main to launch GUI and main page controller.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainPageFXML.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public static void main(String[] args) {

        MainPage.addTestData();
        launch(args); }
}



