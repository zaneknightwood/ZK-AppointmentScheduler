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
import model.Countries;
import model.Customer;
import model.FirstLevelDivision;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class controls the application window for the ModifyCustomer.fxml screen.
 */

public class ModifyCustomer implements Initializable {
    public TextField phone;
    public TextField postCode;
    public TextField address;
    public TextField name;
    public ComboBox<Countries> country;
    public ComboBox<FirstLevelDivision> stateProv;
    public TextField custID;
    public Button cancelButton;
    public Button saveButton;

    private Customer custToModify = null;
    private User thisUser = null;
    private static ObservableList<Countries> allCountries = FXCollections.observableArrayList();
    private static ObservableList<FirstLevelDivision> allFirstLevel = FXCollections.observableArrayList();


    /**
     * The initialize method sets up the screen on launch. It initializes all necessary Observable Lists and collects the
     * customer information from the Customer Records controller. It sets all fields to the information contained in the
     * collected customer. It also collects the user information from the Customer Records controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        custToModify = CustomerRecords.getCustToModify();
        thisUser = CustomerRecords.getThisUser();
        allCountries = Queries.getAllCountries();
        allFirstLevel = Queries.getAllFirst();


        phone.setText(custToModify.getPhoneNum());
        postCode.setText(custToModify.getPostCode());
        address.setText(custToModify.getAddress());
        name.setText(custToModify.getCustName());
        country.setItems(allCountries);
        country.setValue(FirstLevelDivision.getCountryName(custToModify, allFirstLevel, allCountries));
        stateProv.setItems(FirstLevelDivision.getAllFirstLevelByCustomer(custToModify, allFirstLevel, allCountries));
        stateProv.setValue(FirstLevelDivision.getFirstLevelName(custToModify, allFirstLevel));
        custID.setText(String.valueOf(custToModify.getCustID()));

    }

    /**
     * The onCountry method sets the State/Province combo box to the correct list of states or provinces based on the country selected.
     *
     * @param actionEvent The "Country" selection click on the Modify Customer screen.
     */
    public void onCountry(ActionEvent actionEvent) {
        stateProv.setItems((FirstLevelDivision.getAllFirstLevelByCountry(country.getSelectionModel().getSelectedItem(), allFirstLevel)));
    }

    /**
     * The onCancel method alerts the user that the customer information has not been saved and returns to Customer Records on confirmation.
     *
     * @param actionEvent The "Cancel" button click on the Modify Customer screen.
     * @throws IOException Signals that an IOException has occurred in the event an issue with loading CustomerRecords.fxml happens.
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Customer Information has not been saved!");
        alert.setContentText("Cancel and return to Customer Records?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerRecords.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Customer Records");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * The onSave method collects all the information entered into the various fields and creates a new customer based on those fields.
     * It then updates the collected customer in the database based on the customerID and the collected information. Error checking is
     * in place to ensure the customer is complete. It then alerts the user that the customer has been saved and returns to Customer Records.
     *
     * @param actionEvent The "Save" button click on the Modify Customer screen.
     */
    public void onSaveButton(ActionEvent actionEvent) {
        try {
            String custName = name.getText();
            String add = address.getText();
            String pc = postCode.getText();
            String pn = phone.getText();
            LocalDateTime now = LocalDateTime.now();
            String lB = thisUser.getUserName();
            int divID = stateProv.getSelectionModel().getSelectedItem().getDivisionID();

            Customer newCustomer = new Customer(custToModify.getCustID(), custName, add, pc, pn, custToModify.getCreateDate(), custToModify.getCreatedBy(), now, lB, divID);

            Updates.updateCustomer(newCustomer);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Customer Updated Successfully");
            alert.showAndWait();

            Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerRecords.fxml"));
            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Customer Records");
            stage.setScene(scene);
            stage.show();

        } catch (RuntimeException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Customer Information");
            alert.setContentText("All fields are required.\n\nPlease enter valid information for each field.\n\n");
            alert.showAndWait();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
