package controller;

import database.Queries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Month;
import model.User;
import utilities.DateAndTimeProcessing;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * This class controls the application window for the ReportsView.fxml screen.
 */

public class ReportsView implements Initializable {
    public RadioButton byMonthRadio;
    public RadioButton byTypeRadio;
    public RadioButton byUserRadio;
    public Label monthTypeUserLabel;
    public ComboBox totalAppComboBox;
    public Label totalAppsLabel;
    public Label numOfApps;
    public ComboBox<Contact> contactID;
    public Button cancelButton;
    public TableView appTable;
    public TableColumn appID;
    public TableColumn title;
    public TableColumn type;
    public TableColumn description;
    public TableColumn startDate;
    public TableColumn endDate;
    public TableColumn startTime;
    public TableColumn endTime;
    public TableColumn custID;

    ObservableList<Appointment> allAppts = FXCollections.observableArrayList();

    /**
     * The initialize method sets up the screen on launch. It initializes the allAppts Observable List and sets the items
     * in the combo boxes.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        Month.setUpMonths();
        totalAppComboBox.setItems(Month.getAllMonths());
        contactID.setItems(Queries.getAllContacts());
        allAppts = Queries.getAllAppointments();
    }

    /**
     * The onByMonth method sets the items in the combo box to a list of all months in the year, sets the labels to month, and sets
     * the value of total appointments to "##".
     *
     * @param actionEvent The "By Month" radio click on the Reports screen.
     */
    public void onByMonth(ActionEvent actionEvent) {
        monthTypeUserLabel.setText("Month");
        totalAppsLabel.setText("Total Customer Appointments By Month:");
        numOfApps.setText("##");
        totalAppComboBox.setItems(Month.getAllMonths());
    }

    /**
     * The onByType method sets the items in the combo box to a list of all appointment types in the database, sets the labels to type,
     * and sets the value of total appointments to "##".
     *
     * @param actionEvent The "By Type" radio click on the Reports screen.
     */
    public void onByType(ActionEvent actionEvent) {
        monthTypeUserLabel.setText("Type");
        totalAppsLabel.setText("Total Customer Appointments By Type:");
        numOfApps.setText("##");
        totalAppComboBox.setItems(Appointment.getAllTypes(allAppts));
    }

    /**
     * The onByUser method sets the items in the combo box to a list of all users in the database, sets the labels to user,
     * and sets the value of total appointments to "##".
     *
     * NOTE! This is the additional report from part A3f.
     *
     * @param actionEvent The "By User" radio click on the Reports screen.
     */
    public void onByUser(ActionEvent actionEvent) {
        monthTypeUserLabel.setText("User");
        totalAppsLabel.setText("Total Customer Appointments By User:");
        numOfApps.setText("##");
        totalAppComboBox.setItems(Queries.getAllUsers());
    }

    /**
     * The onContactID method sets the items in the table view to only the appointments connected to the selected contact based
     * on the contactID.
     *
     * LAMBDA: There are five lambdas in this method. The first sets up a filtered list from an observable list of all appointments
     * in the database. This is filtered such that only appointments whose contactID matches the selected contact's contactID are
     * included. It then sets the items in the table view to this list of appointments. This enhances the code by doing in one line
     * of code what would otherwise require a separate method elsewhere.
     * The next four lambdas each override the updateItem method in the TableCell class so that the date or time is presented in the
     * specified format instead of the default. This enhances the application by setting dates and times in the table in a more readable format.
     *
     * @param actionEvent The "Contact ID" selection click on the Reports screen.
     */
    public void onContactID(ActionEvent actionEvent) {
        Contact contact = contactID.getSelectionModel().getSelectedItem();
        appTable.setItems(new FilteredList<>(allAppts, c -> c.getContactID() == contact.getContactID()));
        appID.setCellValueFactory(new PropertyValueFactory<>("appointID"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        custID.setCellValueFactory(new PropertyValueFactory<>("custID"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        startDate.setCellFactory(tc -> new TableCell<Appointment, LocalDateTime>(){
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
        startTime.setCellValueFactory((new PropertyValueFactory<>("startDateTime")));
        startTime.setCellFactory(tc -> new TableCell<Appointment, LocalDateTime>(){
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
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        endDate.setCellFactory(tc -> new TableCell<Appointment, LocalDateTime>(){
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
        endTime.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        endTime.setCellFactory(tc -> new TableCell<Appointment, LocalDateTime>(){
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
     * The onCancel method returns to the Appointments View screen.
     *
     * @param actionEvent The "Cancel" button click on the Reports screen.
     * @throws IOException Signals that an IOException has occurred in the event an issue with loading AppointmentView.fxml happens.
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentView.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Appointments View");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The onTotalAppComboBox method checks the combo box selection and sets the value of the total appointments based on the selection class.
     * If the selected class is User, the method sets the text for the total appointments to the total number of appointments connected to the
     * selected user based on userID. If the selected class is Month, the method sets the text for the total appointments to the total number
     * of appointments connected to the selected month based on the startDateTime of the appointments and the monthNumber. If the selected class
     * is String, the method sets the text for the total appointments to the total number of appointments connected to the selected type based on
     * the collected string and the appointmentType.
     *
     * LAMBDA: There are three lambdas used in this method. The first sets up a filtered list from an observable list of all appointments in the
     * database. This is filtered such that only appointments whose userID matches the userID of the selected user are included. It then sets the
     * size of the filtered list to the parameter expected in the String.valueOf method.
     * The second lambda also sets up a filtered list from an observable list of all appointments in the database. This list is filtered such that
     * only appointments whose monthValue in the appointment's startDateTime matches the monthNumber of the selected month are included. It then sets
     * the size of the filtered list to the parameter expected in the String.valueOf method.
     * The third lambda also sets up a filtered list from an observable list of all appointments in the database. This is filtered such that only
     * appointments whose appointmentType matches the selected type are included. It then sets the size of the filtered list to the parameter expected
     * in the String.valueOf method.
     * These lambdas enhance the application by doing in three lines of code what would otherwise have required the use of three separate methods.
     *
     * @param actionEvent The totalAppComboBox selection click on the Reports screen.
     */
    public void onTotalAppComboBox(ActionEvent actionEvent) {
        Object selection = totalAppComboBox.getSelectionModel().getSelectedItem();

        if(selection instanceof User){
            numOfApps.setText(String.valueOf(new FilteredList<>(allAppts, u -> u.getUserID() == ((User) selection).getUserID()).size()));

        }else if (selection instanceof Month){
            numOfApps.setText(String.valueOf(new FilteredList<>(allAppts, m -> m.getStartDateTime().getMonthValue() == ((Month) selection).getNumber()).size()));

        }else if (selection instanceof String){
            numOfApps.setText(String.valueOf(new FilteredList<>(allAppts, t -> t.getType().equals(selection)).size()));

        }

    }
}
