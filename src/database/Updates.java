package database;

import model.Appointment;
import model.Customer;
import utilities.DateAndTimeProcessing;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * This class defines the methods that allow the application to update database information and add new information to the database.
 */

public abstract class Updates {

    /**
     * The setAppointment method sends an SQL statement to the database that adds a new appointment to the table appointments.
     *
     * @param appointment The appointment to add to the database.
     * @return An integer that indicates if the database update was successful.
     * @throws SQLException Signals that an SQLException has occurred in the event an issue occurs when updating the database.
     */
    public static int setAppointment(Appointment appointment) throws SQLException{

        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setTimestamp(5, Timestamp.from(DateAndTimeProcessing.toUTC(appointment.getStartDateTime())));
        ps.setTimestamp(6, Timestamp.from(DateAndTimeProcessing.toUTC(appointment.getEndDateTime())));
        ps.setTimestamp(7, Timestamp.from(DateAndTimeProcessing.toUTC(appointment.getCreateDate())));
        ps.setString(8, appointment.getCreatedBy());
        ps.setTimestamp(9, Timestamp.from(DateAndTimeProcessing.toUTC(appointment.getLastUpdate())));
        ps.setString(10, appointment.getLastUpdatedBy());
        ps.setInt(11, appointment.getCustID());
        ps.setInt(12, appointment.getUserID());
        ps.setInt(13, appointment.getContactID());

        int i = ps.executeUpdate();

        return i;
    }

    /**
     * The setCustomer method sends an SQL statement to the database that adds a new customer to the table customers.
     *
     * @param customer The customer to add to the database.
     * @return An integer that indicates if the database update was successful.
     * @throws SQLException Signals that an SQLException has occurred in the event an issue occurs when updating the database.
     */
    public static int setCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setString(1, customer.getCustName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostCode());
        ps.setString(4, customer.getPhoneNum());
        ps.setTimestamp(5, Timestamp.from(DateAndTimeProcessing.toUTC(customer.getCreateDate())));
        ps.setString(6, customer.getCreatedBy());
        ps.setTimestamp(7, Timestamp.from(DateAndTimeProcessing.toUTC(customer.getLastUpdate())));
        ps.setString(8, customer.getLastUpdatedBy());
        ps.setInt(9, customer.getDivisionID());

        int i = ps.executeUpdate();

        return i;
    }

    /**
     * The updateAppointment method sends an SQL statement to the database that updates an appointment in the table appointments
     * based on the appointmentID.
     *
     * @param appointment The appointment to update.
     * @return An integer that indicates if the database update was successful.
     * @throws SQLException Signals that an SQLException has occurred in the event an issue occurs when updating the database.
     */
    public static int updateAppointment(Appointment appointment) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setTimestamp(5, Timestamp.from(DateAndTimeProcessing.toUTC(appointment.getStartDateTime())));
        ps.setTimestamp(6, Timestamp.from(DateAndTimeProcessing.toUTC(appointment.getEndDateTime())));
        ps.setTimestamp(7, Timestamp.from(DateAndTimeProcessing.toUTC(appointment.getCreateDate())));
        ps.setString(8, appointment.getCreatedBy());
        ps.setTimestamp(9, Timestamp.from(DateAndTimeProcessing.toUTC(appointment.getLastUpdate())));
        ps.setString(10, appointment.getLastUpdatedBy());
        ps.setInt(11,appointment.getCustID());
        ps.setInt(12, appointment.getUserID());
        ps.setInt(13, appointment.getContactID());
        ps.setInt(14, appointment.getAppointID());

        int i = ps.executeUpdate();

        return i;
    }

    /**
     * The updateCustomer method sends an SQL statement to the database that updates a customer in the table customers based
     * on the customerID.
     *
     * @param customer The customer to be updated.
     * @return An integer that indicates if the database update was successful.
     * @throws SQLException Signals that an SQLException has occurred in the event an issue occurs when updating the database.
     */
    public static int updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setString(1, customer.getCustName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostCode());
        ps.setString(4, customer.getPhoneNum());
        ps.setTimestamp(5, Timestamp.from(DateAndTimeProcessing.toUTC(customer.getCreateDate())));
        ps.setString(6, customer.getCreatedBy());
        ps.setTimestamp(7, Timestamp.from(DateAndTimeProcessing.toUTC(customer.getLastUpdate())));
        ps.setString(8, customer.getLastUpdatedBy());
        ps.setInt(9, customer.getDivisionID());
        ps.setInt(10, customer.getCustID());

        int i = ps.executeUpdate();

        return i;
    }

    /**
     * The deleteAppointment method sends an SQL statement to the database that removes an appointment from the table appointments
     * based on the appointmentID.
     *
     * @param appointment The appointment to be deleted.
     * @return An integer that indicates if the database update was successful.
     * @throws SQLException Signals that an SQLException has occurred in the event an issue occurs when updating the database.
     */
    public static int deleteAppointment(Appointment appointment) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setInt(1, appointment.getAppointID());

        int i = ps.executeUpdate();

        return i;
    }

    /**
     * The deleteCustomer method sends an SQL statement to the database that removes a customer from the table customers based
     * on the customerID.
     *
     * @param customer The customer to be deleted.
     * @return An integer that indicates if the database update was successful.
     * @throws SQLException Signals that an SQLException has occurred in the event an issue occurs when updating the database.
     */
    public static int deleteCustomer(Customer customer) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setInt(1, customer.getCustID());

        int i = ps.executeUpdate();

        return i;
    }
}
