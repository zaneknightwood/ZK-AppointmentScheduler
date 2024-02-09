package controller;

import database.Queries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.User;
import utilities.LoginActivity;

import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class controls the application window for the Login.fxml screen.
 */

public class LogIn implements Initializable {
    public TextField userName;
    public PasswordField password;
    public Label userNameLabel;
    public Label passLabel;
    public Label timeZone;
    public Button loginButton;

    private static User thisUser = null;
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /** @return The user. */
    public static User getThisUser(){return thisUser;}

    /**
     * The initialize method sets up the screen on launch. It initializes all necessary Observable Lists and
     * sets the current time and time zone based on the current time in the system's local time zone. It also checks
     * the system's default language settings and displays all text in the system language.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allAppointments = Queries.getAllAppointments();
        allUsers = Queries.getAllUsers();
        ZonedDateTime currentTime = ZonedDateTime.now();
        String currentZone = currentTime.format(DateTimeFormatter.ofPattern("  yyyy-MM-dd\nhh:mm a zzz"));
        ResourceBundle rb = ResourceBundle.getBundle("utilities/LanguageConversion", Locale.getDefault());
        timeZone.setText(currentZone);

        if(Locale.getDefault().getLanguage().equals("fr")) {
            timeZone.setText(currentZone);
            userNameLabel.setText(rb.getString("user"));
            passLabel.setText(rb.getString("password"));
            loginButton.setText(rb.getString("login"));
            userName.setLayoutX(125);
            password.setLayoutX(125);
        }
    }

    /**
     * The onLoginButton method loads the Appointments View screen and passes it the user information based on the
     * username entered into the text field "userName". Error checking is in place to ensure the user login information
     * is valid based on the user list stored in the database. A report is appended each time a user attempts to login.
     * This report includes the username, date and time, and success information. It can be found in the Project folder
     * as login_activity.txt
     *
     * @param actionEvent The "Log In" button click on the Appointments View screen.
     * @throws IOException Signals that an IOException has occurred in the event an issue with loading AppointmentView.fxml happens.
     */
    public void onLoginButton(ActionEvent actionEvent) throws IOException {
        String uText = userName.getText();
        String pText = password.getText();
        boolean isCorrect = User.correctUserPassword(uText, pText, allUsers);

        if (isCorrect) {
            LoginActivity.printToLog(uText, "Login successful.");
            thisUser = User.userFromName(userName.getText(), allUsers);
            Appointment.getApptsWithin15Min(allAppointments, thisUser);

            Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentView.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Appointments View");
            stage.setScene(scene);
            stage.show();

        }
    }
}