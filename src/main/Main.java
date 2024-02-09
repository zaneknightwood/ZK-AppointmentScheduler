package main;

import database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The Main class loads an application that allows a user to maintain a calendar of Appointments.
 *
 * @author Zane Knightwood ID:001403090
 *
 * LAMBDAS can be found in the following class methods: AppointmentView.initialize, ReportsView.onContactId, ReportsView.onTotalAppComboBox, and Appointment.getByMonth
 */

public class Main extends Application {

    /**
     * The start method sets the stage and scene for the initial window of the application. It provides the instructions for loading Login.fxml.
     * @param primaryStage The stage for Login.fxml.
     * @throws Exception Throws an exception if an error is encountered.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        ResourceBundle rb = ResourceBundle.getBundle("utilities/LanguageConversion", Locale.getDefault());

        if(Locale.getDefault().getLanguage().equals("fr")) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
            primaryStage.setTitle(rb.getString("apptSched"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }else {
            Parent root = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
            primaryStage.setTitle("Appointment Scheduler");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
    }

    /**
     *This method starts the database connection and launches the application.
     *Upon closing the application, the database connection is also closed.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}



