package model;

import javafx.collections.ObservableList;

/**
 * The Contacts class defines an instance of Contact.
 */

public class Contact {

    private int contactID;
    private String contactName;
    private String email;

    public Contact(int contactID, String contactName, String email){
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * @return The contact ID.
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * @return The contact name.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @return The contact email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param contactID The contact ID to set.
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * @param contactName The contact name to set.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * @param email The contact email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * The contactNameFromAppointment method searches the list of all contacts for a contact whose contactID matches the contactID of
     * the appointment parameter. When it finds the matching contact, it returns it.
     *
     * @param appointment The appointment whose contactID must be matched.
     * @param allContacts An observable list of all contacts in the database.
     * @return The contact whose contactID matches the appointment parameter's contactID.
     */
    public static Contact contactNameFromAppointment(Appointment appointment, ObservableList<Contact> allContacts){
        Contact toReturn = null;
        for(Contact c : allContacts){
            if(appointment.getContactID() == c.contactID){
                toReturn = c;
                break;
            }
        }
        return toReturn;
    }

    /**
     * This method overrides the toString method so that the contactID and contactName are returned instead of the default.
     *
     * @return A string representing the contact ID and name.
     */
    @Override
    public String toString(){
        return contactID + "- " + contactName;
    }
}
