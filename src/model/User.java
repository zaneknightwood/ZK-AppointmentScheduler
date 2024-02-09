package model;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import utilities.LoginActivity;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The User class defines an instance of User.
 */

public class User {

    private int userID;
    private String userName;
    private String password;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    public User(int userID, String userName, String password, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy){
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * The userNameFromAppt method searches through a list of all users in the database for a user whose
     * userID matches the appointment parameter's userID. If a match is found, the user is returned.
     *
     * @param appointment The appointment whose userID must be matched
     * @param allUsers An observable list of all users in the database.
     * @return The user whose userID matches the appointment parameter's userID.
     */
    public static User userNameFromAppt(Appointment appointment, ObservableList<User> allUsers) {
        User toReturn = null;

        for(User u : allUsers){
            if (appointment.getUserID() == u.getUserID()) {
                toReturn = u;
                break;
            }
        }
        return toReturn;
    }

    /**
     * The correctUserPassword method searches through a list of all  users in the database for a user whose userName and password
     * match the userName and password parameters. If a match is found, the boolean is set to true. If no match is found an alert is
     * shown telling the user that their username or password was incorrect. Error checking is in place with user alerts for if either
     * the userName or password parameters are blank. A report is appended if a match is found, not found, or if one or both parameters
     * are blank. This report includes the username, date and time, and success information. It can be found in the Project folder
     * as login_activity.txt
     *
     * @param password The password that must be matched.
     * @param userName The userName that must be matched.
     * @param allUsers An observable list of all users in the database.
     * @return A boolean that is set to true if both the username and password match a user in the database and false if they do not.
     * @throws IOException Signals that an IOException has occurred in the event an issue with loading any of the alerts happens.
     */
    public static boolean correctUserPassword(String password, String userName, ObservableList<User> allUsers) throws IOException {
        boolean isCorrect = false;
        ResourceBundle rb = ResourceBundle.getBundle("utilities/LanguageConversion", Locale.getDefault());

        if (!password.isEmpty() && !userName.isEmpty()) {
            for (User u : allUsers) {
                if (u.userName.equals(userName) && u.password.equals(password)) {
                    isCorrect = true;
                    break;
                }
            }
            if (!isCorrect && Locale.getDefault().getLanguage().equals("fr")) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle(rb.getString("error"));
                error.setHeaderText(rb.getString("userError"));
                error.showAndWait();
                LoginActivity.printToLog(userName, "Login Failed: Username or Password incorrect.");
            } else if (!isCorrect) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("UserName or Password is incorrect");
                error.showAndWait();
                LoginActivity.printToLog(userName, "Login Failed: Username or Password incorrect.");
            }
        } else {
            if(Locale.getDefault().getLanguage().equals("fr")) {
                Alert noName = new Alert(Alert.AlertType.ERROR);
                noName.setTitle(rb.getString("error"));
                noName.setHeaderText(rb.getString("enterUserName"));
                noName.showAndWait();
                LoginActivity.printToLog(userName, "Login Failed: Username or Password left blank.");
            } else {
                Alert noName = new Alert(Alert.AlertType.ERROR);
                noName.setTitle("Error");
                noName.setHeaderText("Please enter Username and Password");
                noName.showAndWait();
                LoginActivity.printToLog(userName, "Login Failed: Username or Password left blank.");
            }
        }
        return isCorrect;
    }

    /**
     * The userFromName method searches through a list of all users in the database to find a user whose userName matches
     * the parameter userName. If a match is found, the user is returned.
     *
     * @param userName The userName that must be matched.
     * @param allUsers An observable list of all users in the database.
     * @return The user whose userName matches the userName parameter.
     */
    public static User userFromName(String userName, ObservableList<User> allUsers){
        User toReturn = null;

        for (User u : allUsers) {
            if (u.userName.equals(userName)) {
                toReturn = u;
            }
        }
        return toReturn;
    }

    /**
     * @return The userID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @param userID The userID to set.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * @return The userName.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName The userName to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return The createDate.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate The createDate to set.
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * @return The createdBy.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy The createdBy to set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return The lastUpdate.
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate The lastUpdate to set.
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return The lastUpdatedBy.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * @param lastUpdatedBy The lastUpdatedBy to set.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method overrides the toString method so that the userID and userName strings are returned instead of the default.
     *
     * @return A string representing the user ID and name.
     */
    @Override
    public String toString() {return userID + "- " + userName;}

}
