package controller;

import database.Queries;
import database.Updates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import utilities.DateAndTimeProcessing;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class controls the application window for the AddAppointment.fxml screen.
 */

public class AddAppointment implements Initializable {
    public Button saveButton;
    public Button cancelButton;
    public TextField appID;
    public TextArea description;
    public TextField title;
    public TextField loc;
    public ComboBox<Contact> contact;
    public TextField type;
    public DatePicker startDate;
    public DatePicker endDate;
    public ComboBox<LocalTime> startTimeCombo;
    public ComboBox<LocalTime> endTimeCombo;
    public ComboBox<User> userID;
    public ComboBox<Customer> customerID;


    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private User thisUser = null;

    /**
     * The initialize method sets up the screen on launch. It initializes all necessary Observable Lists, sets the items
     * in the combo boxes, and sets the date pickers to the current date in the system's local time zone. It also collects the
     * user information from the Appointment View controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        allContacts = Queries.getAllContacts();
        allUsers = Queries.getAllUsers();
        allCustomers = Queries.getAllCustomers();
        allAppointments = Queries.getAllAppointments();
        thisUser = AppointmentView.getThisUser();

        LocalTime start = LocalTime.of(1,0);
        LocalTime end = LocalTime.of(23,0);

        contact.setItems(allContacts);
        userID.setItems(allUsers);
        customerID.setItems(allCustomers);
        startDate.setValue(LocalDate.now());
        endDate.setValue(LocalDate.now());

        while(start.isBefore(end.plusSeconds(1))) {
            startTimeCombo.getItems().add(start);
            endTimeCombo.getItems().add(start);
            start = start.plusMinutes(15);
        }
    }

    /**
     * The onSave method collects all the information entered into the various fields and creates a new appointment based on those fields.
     * It then adds the new Appointment to the database. Error checking is in place to ensure the appointment is complete, within the business
     * operating hours of 8am - 10pm EST Monday - Friday, and does not conflict with any other appointment for the specified customer. It then
     * alerts the user that the appointment has been saved and returns to Appointment View.
     *
     * @param actionEvent The "Save" button click on the Add Appointment screen.
     */
    public void onSave(ActionEvent actionEvent) {
        try {
            int aID = 0;
            String t = title.getText();
            String d = description.getText();
            String l = loc.getText();
            int coID = contact.getSelectionModel().getSelectedItem().getContactID();
            String ty = type.getText();
            LocalDateTime st = LocalDateTime.of(startDate.getValue(), startTimeCombo.getSelectionModel().getSelectedItem());
            LocalDateTime en = LocalDateTime.of(endDate.getValue(), endTimeCombo.getSelectionModel().getSelectedItem());
            int cuID = customerID.getSelectionModel().getSelectedItem().getCustID();
            int usID = userID.getSelectionModel().getSelectedItem().getUserID();
            LocalDateTime now = LocalDateTime.now();
            String userName = thisUser.getUserName();
            boolean isInsideHours = DateAndTimeProcessing.checkAppointmentInsideHours(st, en);

            if (isInsideHours) {
                Appointment newAppt = new Appointment(aID, t, d, l, ty, st, en, now, userName, now, userName, cuID, usID, coID);
                Appointment conflictApp = Appointment.appointmentOverlap(newAppt, allAppointments);
                if (conflictApp == null) {
                    Updates.setAppointment(newAppt);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Appointment Added Successfully");
                    alert.showAndWait();

                    Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentView.fxml"));
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setTitle("Appointments View");
                    stage.setScene(scene);
                    stage.show();
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Appointment conflicts with a previously set appointment.");
                    alert.setContentText("Customer: " + conflictApp.getCustID() + " has a conflicting appointment.\nAppointment # " + conflictApp.getAppointID() + "\nStart: " + DateAndTimeProcessing.ymdhma(conflictApp.getStartDateTime()) + "\nEnd: " + DateAndTimeProcessing.ymdhma(conflictApp.getEndDateTime()));
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Appointment time must be between 8am and 10pm EST\nand between Monday and Friday");
                alert.showAndWait();
            }
        } catch (RuntimeException | IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Appointment Information");
            alert.setContentText("All fields are required.\n\nPlease enter valid information for each field.\n\n");
            alert.showAndWait();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * The onCancel method alerts the user that the appointment information has not been saved and returns to Appointments View on confirmation.
     *
     * @param actionEvent The "Cancel" button click on the Add Appointment screen.
     * @throws IOException Signals that an IOException has occurred in the event an issue with loading AppointmentView.fxml happens.
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Appointment has not been saved!");
        alert.setContentText("Cancel and return to Appointment View?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentView.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Appointments View");
            stage.setScene(scene);
            stage.show();
        }
}

    /**
     * The onStartTime method sets the end time combo box value to 30 minutes after the selected start time.
     *
     * @param actionEvent The "Start Time" selection click on the Add Appointment Screen.
     */
    public void onStartTime(ActionEvent actionEvent) {
        LocalTime start = startTimeCombo.getValue();
        endTimeCombo.getSelectionModel().select(start.plusMinutes(30));
    }

    /**
     * The onEndTime method checks if the start time is after the end time and throws an error if it is.
     *
     * @param actionEvent The "End Time" selection click on the Add Appointment Screen.
     */
    public void onEndTime(ActionEvent actionEvent) {
        LocalTime start = startTimeCombo.getValue();
        LocalTime end = endTimeCombo.getValue();

        if (start.isAfter(end)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("End time cannot be before start time.");
            alert.showAndWait();
        }
    }

    /**
     * The onStartDate method sets the end date date picker to the selected start date.
     *
     * @param actionEvent The "Start Date" selection click on the Add Appointment Screen.
     */
    public void onStartDate(ActionEvent actionEvent) {
        LocalDate startD = startDate.getValue();
        endDate.setValue(startD);
    }

    /**
     * The onEndDate method checks if the start date is after the end date and throws an error if it is.
     *
     * @param actionEvent The "End Date" selection click on the Add Appointment Screen.
     */
    public void onEndDate(ActionEvent actionEvent) {
        LocalDate startD = startDate.getValue();
        LocalDate endD = endDate.getValue();

        if (startD.isAfter(endD)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("End date cannot be before start date.");
            alert.showAndWait();
        }
    }
}
