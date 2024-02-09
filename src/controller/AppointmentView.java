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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.User;
import utilities.DateAndTimeProcessing;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class controls the application window for the AppointmentView.fxml screen.
 */

public class AppointmentView implements Initializable {
    public TableColumn appStartDateCol;
    public TableView<Appointment> appTable;
    public TableColumn appIDCol;
    public TableColumn appTitleCol;
    public TableColumn appDescCol;
    public TableColumn appLocCol;
    public TableColumn appContCol;
    public TableColumn appTypeCol;
    public TableColumn appEndDateCol;
    public TableColumn appStartTimeCol;
    public TableColumn appEndTimeCol;
    public TableColumn appCustIDCol;
    public TableColumn appUserIDCol;
    public Button custTableButton;
    public Button reportButton;
    public Button addAppButton;
    public Button modAppButton;
    public Button deleteAppButton;
    public Button logOutButton;
    public RadioButton monthRadio;
    public RadioButton weekRadio;
    public RadioButton allRadio;

    private static Appointment apptToModify = null;
    private static User thisUser = null;
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /** @return The appointment. */
    public static Appointment getApptToModify(){return apptToModify;}

    /** @return The user. */
    public static User getThisUser(){return thisUser;}



    /**
     * The initialize method sets up the screen on launch. It initializes all necessary Observable Lists, sets the items
     * in the table view, and formats the dates and times on the table view into a more readable format. It also collects
     * the user information from the Login controller.
     *
     * LAMBDA: There are four lambdas in this method. Each overrides the updateItem method in the TableCell class
     * so that the date or time is presented in the specified format instead of the default. This enhances the application
     * by setting dates and times in the table in a more readable format.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        allAppointments = Queries.getAllAppointments();
        thisUser = LogIn.getThisUser();


        appTable.setItems(allAppointments);
        appIDCol.setCellValueFactory(new PropertyValueFactory<>("appointID"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appContCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appCustIDCol.setCellValueFactory(new PropertyValueFactory<>("custID"));
        appUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appStartDateCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        appStartDateCol.setCellFactory(tc -> new TableCell<Appointment, LocalDateTime>(){
            @Override
            protected void updateItem(LocalDateTime date, boolean empty){
                super.updateItem(date, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(DateAndTimeProcessing.ymd(date));
                }
            }
        });
        appStartTimeCol.setCellValueFactory((new PropertyValueFactory<>("startDateTime")));
        appStartTimeCol.setCellFactory(tc -> new TableCell<Appointment, LocalDateTime>(){
            @Override
            protected void updateItem(LocalDateTime time, boolean empty){
                super.updateItem(time, empty);
                if(empty){
                    setText(null);
                } else {
                    setText(DateAndTimeProcessing.hma(time));
                }
            }
        });
        appEndDateCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        appEndDateCol.setCellFactory(tc -> new TableCell<Appointment, LocalDateTime>(){
            @Override
            protected void updateItem(LocalDateTime date, boolean empty){
                super.updateItem(date, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(DateAndTimeProcessing.ymd(date));
                }
            }
        });
        appEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        appEndTimeCol.setCellFactory(tc -> new TableCell<Appointment, LocalDateTime>(){
            @Override
            protected void updateItem(LocalDateTime time, boolean empty){
                super.updateItem(time, empty);
                if(empty){
                    setText(null);
                } else {
                    setText(DateAndTimeProcessing.hma(time));
                }
            }
        });
    }

    /**
     * The onCustTableButton method loads the Customer Records screen.
     *
     * @param actionEvent The "Customers" button click on the Appointments View screen.
     * @throws IOException Signals that an IOException has occurred in the event an issue with loading CustomerRecords.fxml happens.
     */
    public void onCustTableButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerRecords.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Customer Records");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The onReportButton method loads the Reports screen.
     *
     * @param actionEvent The "Reports" button click on the Appointments View screen.
     * @throws IOException Signals that an IOException has occurred in the event an issue with loading Reports.fxml happens.
     */
    public void onReportButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportsView.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The onAddAppButton method loads the Add Appointment screen.
     *
     * @param actionEvent The "Add Appointment" button click on the Appointments View screen.
     * @throws IOException Signals that an IOException has occurred in the event an issue with loading AddAppointment.fxml happens.
     */
    public void onAddAppButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The onModAppButton method loads the Modify Appointment screen and passes it the selected appointment from the table view. Error
     * checking is in place to ensure an appointment is selected.
     *
     * @param actionEvent The "Modify Appointment" button click on the Appointments View screen.
     * @throws IOException Signals that an IOException has occurred in the event an issue with loading ModifyAppointment.fxml happens.
     */
    public void onModAppButton(ActionEvent actionEvent) throws IOException {
        apptToModify = appTable.getSelectionModel().getSelectedItem();

        if(apptToModify != null) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyAppointment.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Appointment");
            stage.setScene(scene);
            stage.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning!");
            alert.setHeaderText("No Appointment Selected!");
            alert.setContentText("Please select appointment to modify.");
            alert.showAndWait();
        }
    }

    /**
     * The onDeleteAppButton method confirms that the user wants to delete the selected appointment and removes it from the database upon
     * confirmation. An alert confirms appointment delete. Error checking is in place to ensure an appointment is selected.
     *
     * @param actionEvent The "Delete" button click on the Appointments View screen.
     * @throws SQLException Signals that an SQLException has occurred in the event an issue occurs when updating the database.
     */
    public void onDeleteAppButton(ActionEvent actionEvent) throws SQLException {
        Appointment appointment = appTable.getSelectionModel().getSelectedItem();

        if(appointment != null) {
            Alert deleteWarning = new Alert(Alert.AlertType.CONFIRMATION);
            deleteWarning.setTitle("Warning!");
            deleteWarning.setHeaderText("Are you sure you want to delete this appointment?");
            deleteWarning.setContentText("Appointments cannot be recovered after deletion.\n\nContinue?\n\n");

            Optional<ButtonType> result = deleteWarning.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Updates.deleteAppointment(appointment);
                allAppointments = Queries.getAllAppointments();
                appTable.setItems(allAppointments);
                Alert deleteSuccess = new Alert(Alert.AlertType.INFORMATION);
                deleteSuccess.setTitle("Success!");
                deleteSuccess.setHeaderText("Appointment ID: " + appointment.getAppointID() + " of Type: " + appointment.getType() + " has been deleted.");
                deleteSuccess.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning!");
            alert.setHeaderText("No Appointment Selected!");
            alert.setContentText("Please select appointment to modify.");
            alert.showAndWait();
        }
    }

    /**
     * The onLogOut method confirms that the user wishes to log out of the program and returns to Login on confirmation.
     *
     * @param actionEvent The "Log Out" button click on the Appointments View screen.
     * @throws IOException Signals that an IOException has occurred in the event an issue with loading LogIn.fxml happens.
     */
    public void onLogOut(ActionEvent actionEvent) throws IOException {
        Alert deleteWarning = new Alert(Alert.AlertType.CONFIRMATION);
        deleteWarning.setTitle("Warning!");
        deleteWarning.setHeaderText("Are you sure you want to Log Out?");

        Optional<ButtonType> result = deleteWarning.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Appointment Scheduler");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * The onMonthRadio method sets the items in the table view to only the appointments that occur in the
     * same calendar month as the system's current date.
     *
     * @param actionEvent The "This Month" radio click on the Appointments View screen.
     */
    public void onMonthRadio(ActionEvent actionEvent) {
        appTable.setItems(Appointment.getByMonth(allAppointments));
    }

    /**
     * The onWeekRadio method sets the items in the table view to only the appointments that occur in the upcoming
     * week based on the system's current date. This view does not show past appointments. In the event that the
     * current day is a Saturday, the next week's appointments will be shown.
     *
     * @param actionEvent The "This Week" radio click on the Appointments View screen.
     */
    public void onWeekRadio(ActionEvent actionEvent) {
        appTable.setItems(Appointment.getByWeek(allAppointments));
    }

    /**
     * The onAll method sets the items in the table view to show all appointments in the database.
     *
     * @param actionEvent The "All Appointments" button click on the Appointments View screen.
     */
    public void onAll(ActionEvent actionEvent) {
        appTable.setItems(allAppointments);
    }
}
