package model;

import javafx.collections.ObservableList;

import java.time.LocalDateTime;

/**
 * The Customer class defines an instance of Customer.
 */

public class Customer {

    private int custID;
    private String custName;
    private String address;
    private String postCode;
    private String phoneNum;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;

    public Customer(int custID, String custName, String address, String postCode, String phoneNum, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int divisionID){
        this.custID = custID;
        this.custName = custName;
        this.address = address;
        this.postCode = postCode;
        this.phoneNum = phoneNum;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
    }

    /**
     * The getCustFromAppt method searches the list of all customers for a customer whose custID matches the custID of
     * the appointment parameter. When it finds the matching customer, it returns it.
     *
     * @param appointment The appointment whose custID must be matched.
     * @param allCustomers An observable list of all customers in the database.
     * @return The customer whose custID matches the appointment parameter's custID.
     */
    public static Customer getCustFromAppt(Appointment appointment, ObservableList<Customer> allCustomers){
        Customer toReturn = null;

        for(Customer c : allCustomers){
            if(appointment.getCustID() == c.custID){
                toReturn = c;
                break;
            }
        }
        return toReturn;
    }

    /**
     * @return The customer ID.
     */
    public int getCustID() {
        return custID;
    }

    /**
     * @return The customer name.
     */
    public String getCustName() {
        return custName;
    }

    /**
     * @return The customer address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return The customer post code.
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * @return The customer phone number.
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * @return The customer createDate.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * @return The customer createdBy.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @return The customer lastUpdate.
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @return The customer lastUpdatedBy.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * @return The customer division ID.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * @param custID The customer ID to set.
     */
    public void setCustID(int custID) {
        this.custID = custID;
    }

    /**
     * @param custName The customer name to set.
     */
    public void setCustName(String custName) {
        this.custName = custName;
    }

    /**
     * @param address The customer address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @param postCode The customer post code to set.
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * @param phoneNum The customer phone number to set.
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * @param createDate The customer createDate to set.
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * @param createdBy The customer createdBy to set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @param lastUpdate The customer lastUpdate to set.
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @param lastUpdatedBy The customer lastUpdatedBy to set.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * @param divisionID The customer division ID to set.
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * This method overrides the toString method so that the custID and custName are returned instead of the default.
     *
     * @return A string representing the customer ID and name.
     */
    @Override
    public String toString() {return custID + ": " + custName;}
}

