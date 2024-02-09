package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * This class defines the methods that allow the application to retrieve information from the database.
 */

public abstract class Queries {

    /**
     * The getAllAppointments method sends a query statement to the database and collects all the appointments from the appointments table.
     * It then adds them to an observable list that can be accessed and manipulated by the application.
     *
     * @return An observable list of Appointment objects.
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String desc = rs.getString("Description");
                String loc = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp startTS = rs.getTimestamp("Start");
                LocalDateTime start = startTS.toLocalDateTime();
                Timestamp endTS = rs.getTimestamp("End");
                LocalDateTime end = endTS.toLocalDateTime();
                Timestamp createTS = rs.getTimestamp("Create_Date");
                LocalDateTime createD = createTS.toLocalDateTime();
                String createB = rs.getString("Created_By");
                Timestamp lastTS = rs.getTimestamp("Last_Update");
                LocalDateTime lastUp = lastTS.toLocalDateTime();
                String lastB = rs.getString("Last_Updated_By");
                int custID = rs.getInt("Customer_ID");
                int useID = rs.getInt("User_ID");
                int conID = rs.getInt("Contact_ID");
                Appointment a = new Appointment(appID, title, desc, loc, type, start, end, createD, createB, lastUp, lastB, custID, useID, conID);
                allAppointments.add(a);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

    /**
     * The getAllContacts method sends a query statement to the database and collects all the contacts from the contacts table.
     * It then adds them to an observable list that can be accessed and manipulated by the application.
     *
     * @return An observable list of Contact objects.
     */
    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM contacts";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int conID = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contact c = new Contact(conID, name, email);
                allContacts.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allContacts;
    }

    /**
     * The getAllCountries method sends a query statement to the database and collects all the countries from the countries table.
     * It then adds them to an observable list that can be accessed and manipulated by the application.
     *
     * @return An observable list of Countries objects.
     */
    public static ObservableList<Countries> getAllCountries() {
        ObservableList<Countries> allCountries = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int cID = rs.getInt("Country_ID");
                String name = rs.getString("Country");
                Timestamp createTS = rs.getTimestamp("Create_Date");
                LocalDateTime createD = createTS.toLocalDateTime();
                String createB = rs.getString("Created_By");
                Timestamp lastTS = rs.getTimestamp("Last_Update");
                LocalDateTime lastUp = lastTS.toLocalDateTime();
                String lastB = rs.getString("Last_Updated_By");

                Countries c = new Countries(cID, name, createD, createB, lastUp, lastB);
                allCountries.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCountries;
    }

    /**
     * The getAllCustomers method sends a query statement to the database and collects all the customers from the customers table.
     * It then adds them to an observable list that can be accessed and manipulated by the application.
     *
     * @return An observable list of Customer objects.
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM customers";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int custID = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String ad = rs.getString("Address");
                String pc = rs.getString("Postal_Code");
                String num = rs.getString("Phone");;
                Timestamp createTS = rs.getTimestamp("Create_Date");
                LocalDateTime createD = createTS.toLocalDateTime();
                String createB = rs.getString("Created_By");
                Timestamp lastTS = rs.getTimestamp("Last_Update");
                LocalDateTime lastUp = lastTS.toLocalDateTime();
                String lastB = rs.getString("Last_Updated_By");
                int div = rs.getInt("Division_ID");

                Customer c = new Customer(custID, name, ad, pc, num, createD, createB, lastUp, lastB, div);
                allCustomers.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomers;
    }

    /**
     * The getAllFirst method sends a query statement to the database and collects all the first level divisions from the first_level_divisions table.
     * It then adds them to an observable list that can be accessed and manipulated by the application.
     *
     * @return An observable list of FirstLevelDivision objects.
     */
    public static ObservableList<FirstLevelDivision> getAllFirst() {
        ObservableList<FirstLevelDivision> allFirst = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM first_level_divisions";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int div = rs.getInt("Division_ID");
                String name = rs.getString("Division");
                Timestamp createTS = rs.getTimestamp("Create_Date");
                LocalDateTime createD = createTS.toLocalDateTime();
                String createB = rs.getString("Created_By");
                Timestamp lastTS = rs.getTimestamp("Last_Update");
                LocalDateTime lastUp = lastTS.toLocalDateTime();
                String lastB = rs.getString("Last_Updated_By");
                int cID = rs.getInt("Country_ID");

                FirstLevelDivision f = new FirstLevelDivision(div, name, createD, createB, lastUp, lastB, cID);
                allFirst.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allFirst;
    }

    /**
     * The getAllUsers method sends a query statement to the database and collects all the users from the users table.
     * It then adds them to an observable list that can be accessed and manipulated by the application.
     *
     * @return An observable list of User objects.
     */
    public static ObservableList<User> getAllUsers() {
        ObservableList<User> allUsers = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM users";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int uID = rs.getInt("User_ID");
                String name = rs.getString("User_Name");
                String pass = rs.getString("Password");
                Timestamp createTS = rs.getTimestamp("Create_Date");
                LocalDateTime createD = createTS.toLocalDateTime();
                String createB = rs.getString("Created_By");
                Timestamp lastTS = rs.getTimestamp("Last_Update");
                LocalDateTime lastUp = lastTS.toLocalDateTime();
                String lastB = rs.getString("Last_Updated_By");

                User u = new User(uID, name, pass, createD, createB, lastUp, lastB);
                allUsers.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }
}
