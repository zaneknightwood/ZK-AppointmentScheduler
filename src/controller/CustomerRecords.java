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
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class controls the application window for the CustomerRecords.fxml screen.
 */

public class CustomerRecords implements Initializable {
    public TableView<Customer> custTable;
    public TableColumn custIDCol;
    public TableColumn custNameCol;
    public TableColumn custAddCol;
    public TableColumn custPostCol;
    public TableColumn custPhoneCol;
    public TableColumn custDivIDCol;
    public TableColumn custCreateDateCol;
    public TableColumn custCreateByCol;
    public TableColumn custLastUpdCol;
    public TableColumn custLastUpByCol;
    public Button addCust;
    public Button modCust;
    public Button deleteCust;
    public Button backButton;

    private static Customer custToModify = null;
    private static User thisUser = null;
    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /** @return The customer. */
    public static Customer getCustToModify(){return custToModify;}

    /** @return The user. */
    public static User getThisUser(){return thisUser;}

    /**
     * The initialize method sets up the screen on launch. It initializes all necessary Observable Lists and sets the items
     * in the table view. It also collects the user information from the AppointmentView controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        allCustomers = Queries.getAllCustomers();
        allAppointments = Queries.getAllAppointments();
        thisUser = AppointmentView.getThisUser();

        custTable.setItems(allCustomers);
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("custID"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("custName"));
        custAddCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        custPostCol.setCellValueFactory(new PropertyValueFactory<>("postCode"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        custDivIDCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        custCreateDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        custCreateByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        custLastUpdCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        custLastUpByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));

    }

    /**
     * The onAddCust method loads the Add Customer screen.
     *
     * @param actionEvent The "Add Customer" button click on the Customer Records screen.
     * @throws IOException Signals that an IOException has occurred in the event an issue with loading AddCustomer.fxml happens.
     */
    public void onAddCust(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The onModCust method loads the Modify Customer screen and passes it the selected customer from the table view. Error
     * checking is in place to ensure a customer is selected.
     *
     * @param actionEvent The "Modify Customer" button click on the Customer Records screen.
     * @throws IOException Signals that an IOException has occurred in the event an issue with loading ModifyCustomer.fxml happens.
     */
    public void onModCust(ActionEvent actionEvent) throws IOException {
        custToModify = custTable.getSelectionModel().getSelectedItem();

        if(custToModify != null) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyCustomer.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Customer");
            stage.setScene(scene);
            stage.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning!");
            alert.setHeaderText("No Customer Selected!");
            alert.setContentText("Please select customer to modify.");
            alert.showAndWait();
        }
    }

    /**
     * The onDeleteCust method confirms that the user wants to delete the selected customer and removes it from the database upon
     * confirmation. Error checking is in place to ensure a customer is selected and that no appointments are connected to the
     * selected customer.
     *
     * @param actionEvent The "Delete Customer" button click on the Customer Records screen.
     * @throws SQLException Signals that an SQLException has occurred in the event an issue occurs when updating the database.
     */
    public void onDeleteCust(ActionEvent actionEvent) throws SQLException {
        Customer customer = custTable.getSelectionModel().getSelectedItem();

        if(customer != null) {
            boolean canDelete = Appointment.customerCanBeDeleted(customer, allAppointments);

            if(canDelete) {
                Alert deleteWarning = new Alert(Alert.AlertType.CONFIRMATION);
                deleteWarning.setTitle("Warning!");
                deleteWarning.setHeaderText("Are you sure you want to delete this customer?");
                deleteWarning.setContentText("Customer Information cannot be recovered after deletion.\n\nContinue?\n\n");

                Optional<ButtonType> result = deleteWarning.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Updates.deleteCustomer(customer);
                    allCustomers = Queries.getAllCustomers();
                    custTable.setItems(allCustomers);
                    Alert deleteSuccess = new Alert(Alert.AlertType.INFORMATION);
                    deleteSuccess.setTitle("Success!");
                    deleteSuccess.setHeaderText("Customer ID: " + customer.getCustID() + " Name: " + customer.getCustName() + " has been deleted.");
                    deleteSuccess.showAndWait();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText("This customer still has connected appointments.");
                alert.setContentText("To delete this customer, first delete all connected appointments.");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning!");
            alert.setHeaderText("No Customer Selected!");
            alert.setContentText("Please select customer to modify.");
            alert.showAndWait();
        }
    }

    /**
     * The onBackButton method loads the Appointments View screen.
     *
     * @param actionEvent The "Back to Appointments" button click on the Customer Records screen.
     * @throws IOException Signals that an IOException has occurred in the event an issue with loading AppointmentView.fxml happens.
     */
    public void onBackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentView.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Appointments View");
        stage.setScene(scene);
        stage.show();
    }
}
