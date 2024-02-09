package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import utilities.DateAndTimeProcessing;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * The Appointments class defines an instance of Appointment.
 */

public class Appointment {

    private int appointID;
    private String title;
    private String description;
    private String location;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private String type;
    private int custID;
    private int userID;
    private int contactID;

    public Appointment(int appointID, String title, String description, String location,  String type, LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int custID, int userID, int contactID){
        this.appointID = appointID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.type = type;
        this.custID = custID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * The customerCanBeDeleted method searches through the appointment list to see if any appointment's custID matches the
     * selected customer's custID. If a match is found, the customer should not be deleted.
     *
     * @param customer The customer whose custID should be searched for.
     * @param allAppts An observable list of all appointments in the database.
     * @return A boolean that is set to true if the customer is safe to delete and false if it is not.
     */
    public static boolean customerCanBeDeleted(Customer customer, ObservableList<Appointment> allAppts){
        boolean canDelete = false;

        for(Appointment a : allAppts){
            if (customer.getCustID() == a.getCustID()){
                canDelete = false;
                break;
            } else {
                canDelete = true;
            }
        }
        return canDelete;
    }

    /**
     * The getAllTypes method searches through the appointment list to find all of the appointment types in the database.
     * It creates a new list of strings and adds each type provided it is not already in the list.
     *
     * @param appList An observable list of all appointments in the database.
     * @return An observable list of String objects containing one instance of each appointment type.
     */
    public static ObservableList<String> getAllTypes(ObservableList<Appointment> appList) {
        ObservableList<String> allTypes = FXCollections.observableArrayList();
        for(Appointment a : appList){
            if(allTypes.contains(a.type)){
                continue;
            } else {
                allTypes.add(a.type);
            }
        }
        return allTypes;
    }

    /**
     * The getByMonth method searches through the list of all appointments to find the ones that match the
     * current month.
     *
     * LAMBDA: The lambda in this method sets up a filtered list from an observable list of all appointments
     * in the database. This is filtered such that only appointments whose monthValue in the appointment's
     * startDateTime matches the monthValue of the current month are included. This lambda enhances the application
     * by simplifying the filtered list process into one line of code.
     *
     * @param appList An observable list of all appointments in the database.
     * @return A filtered observable list of Appointment objects containing only those in the current month.
     */
    public static ObservableList<Appointment> getByMonth(ObservableList<Appointment> appList) {
        java.time.Month month = LocalDate.now().getMonth();
        return new FilteredList<>(appList, m -> m.getStartDateTime().getMonthValue() == month.getValue());
    }

    /**
     * The getByWeek method searches through the list of all appointments to find the ones that match the
     * current week. This list does not include past appointments. In the event that the current day is a
     * Saturday, the next week's appointments will be included.
     *
     * @param appList An observable list of all appointments in the database.
     * @return A filtered observable list of Appointment objects containing only those in the current week.
     */
    public static ObservableList<Appointment> getByWeek(ObservableList<Appointment> appList) {
        ObservableList<Appointment> byWeek = FXCollections.observableArrayList();
        LocalDateTime start = LocalDateTime.now().minusMinutes(1);
        LocalDateTime end = start.plusDays(DateAndTimeProcessing.findWeekLength());

        for (Appointment a : appList){
            if (a.getStartDateTime().isAfter(start) && a.getStartDateTime().isBefore(end.plusMinutes(1))){
                byWeek.add(a);
            }
        }
        return byWeek;
    }

    /**
     * The getApptsWithin15Min method searches through the list of all appointments to see if any are within
     * 15 minutes of the system's current time. If any are found, an alert is shown that tells the user what
     * appointments they have in the next 15 minutes. If no appointments meet the criteria, an alert is shown
     * that tells the user they have no upcoming appointments.
     *
     * @param allAppts An observable list of all appointments in the database.
     * @param user The current user.
     */
    public static void getApptsWithin15Min(ObservableList<Appointment> allAppts, User user){
        LocalDateTime now = LocalDateTime.now();
        ObservableList<Appointment> apptNow = FXCollections.observableArrayList();

        for (Appointment a : allAppts) {
            if (user.getUserID() == a.getUserID()) {
                LocalDateTime start = a.startDateTime;
                if (start.isBefore(now.plusMinutes(15)) && start.isAfter(now)) {
                    apptNow.add(a);
                }
            }
        }
        if (!apptNow.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upcoming Appointments");
            alert.setHeaderText("The following appointments occur in the next 15 minutes.");
            String alertText = "Appt ID   |   Date            |    Time\n"; //2 spaces bt id - date, 8 spaces bt date - time
            for (Appointment a : apptNow) {
                String apID = Integer.toString(a.getAppointID());
                String apDate = DateAndTimeProcessing.ymd(a.getStartDateTime());
                String apTime = DateAndTimeProcessing.hma(a.getStartDateTime());
                alertText = alertText + apID + "               " + apDate + "      " + apTime + "\n";
            }
            alert.setContentText(alertText);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upcoming Appointments");
            alert.setHeaderText("There are no appointments scheduled within the next 15 minutes.");
            alert.showAndWait();
        }
    }

    /**
     * The appointmentOverlap method searches through the list of all appointments to see if any appointments already
     * occur during the appointment parameter. If any are found, it returns the conflicting appointment.
     *
     * @param appointment The appointment that might conflict with one already in the database.
     * @param allAppts An observable list of all appointments in the database.
     * @return The appointment already in the database that conflicts with the appointment parameter.
     */
    public static Appointment appointmentOverlap(Appointment appointment, ObservableList<Appointment> allAppts){
        Appointment conflict = null;
        LocalDateTime aStart = appointment.startDateTime;
        LocalDateTime aEnd = appointment.endDateTime;

        for (Appointment b : allAppts) {
            if (appointment.getCustID() == b.getCustID() && appointment.getAppointID() != b.getAppointID()) {
                LocalDateTime bStart = b.startDateTime;
                LocalDateTime bEnd = b.endDateTime;

                if (bEnd.equals(aEnd) || bStart.equals(aStart)) {
                    conflict = b;
                    break;
                } else if (bStart.isBefore(aStart) && bEnd.isAfter(aStart)) {
                    conflict = b;
                    break;
                }else if (bStart.isAfter(aStart) && bStart.isBefore(aEnd)) {
                    conflict = b;
                    break;
                }
            }
        }
        return conflict;
    }

    /**
     * This method overrides the toString method so that the type string is returned instead of the default.
     *
     * @return A string representing the appointment type.
     */
    @Override
    public String toString() {return type;}

    /**
     * @return The appointment ID.
     */
    public int getAppointID() { return appointID;}

    /**
     * @return The appointment title.
     */
    public String getTitle() { return title;}

    /**
     * @return The appointment description.
     */
    public String getDescription() { return description;}

    /**
     * @return The appointment location.
     */
    public String getLocation() { return location;}

    /**
     * @return The appointment startDateTime
     */
    public LocalDateTime getStartDateTime() { return startDateTime;}

    /**
     * @return The appointment endDateTime.
     */
    public LocalDateTime getEndDateTime() { return endDateTime;}

    /**
     * @return The appointment createDate.
     */
    public LocalDateTime getCreateDate() { return createDate;}

    /**
     * @return The appointment createdBy.
     */
    public String getCreatedBy() { return createdBy;}

    /**
     * @return The appointment lastUpdate.
     */
    public LocalDateTime getLastUpdate() { return lastUpdate;}

    /**
     * @return The appointment lastUpdatedBy.
     */
    public String getLastUpdatedBy() { return lastUpdatedBy;}

    /**
     * @return The appointment custID.
     */
    public int getCustID() { return custID;}

    /**
     * @return The appointment userID.
     */
    public int getUserID() { return userID;}

    /**
     * @return THe appointment contactID.
     */
    public int getContactID() { return contactID;}

    /**
     * @return The appointment type.
     */
    public String getType() {return type;}

    /**
     * @param appointID The appointment ID to set.
     */
    public void setAppointID(int appointID) {
        this.appointID = appointID;
    }

    /**
     * @param title The appointment title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @param description The appointment description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param location The appointment location to set.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @param startDateTime The appointment startDateTime to set.
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * @param endDateTime The appointment endDateTime to set.
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * @param createDate The appointment createDate to set.
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * @param createdBy The appointment createdBy to set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @param lastUpdate The appointment lastUpdate to set.
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @param lastUpdatedBy The appointment lastUpdatedBy to set.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * @param custID The appointment custID to set.
     */
    public void setCustID(int custID) {
        this.custID = custID;
    }

    /**
     * @param userID The appointment userID to set.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * @param contactID The appointment ContactID to set.
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * @param type The appointment type to set.
     */
    public void setType(String type) {
        this.type = type;
    }
}
