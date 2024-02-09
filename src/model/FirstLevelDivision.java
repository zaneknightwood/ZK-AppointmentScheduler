package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

/**
 * The FirstLevelDivision class defines an instance of FirstLevelDivision.
 */

public class FirstLevelDivision {

    private int divisionID;
    private String division;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int countryID;


    public FirstLevelDivision(int divisionID, String division, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int countryID){
        this.divisionID = divisionID;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryID = countryID;
    }

    /**
     * @return The divisionID.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * @return The division.
     */
    public String getDivision() {
        return division;
    }

    /**
     * @return The createDate.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * @return The createdBy.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @return The lastUpdate.
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @return The lastUpdatedBy.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * @return The countryID.
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * @param divisionID The divisionID to set.
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * @param division The division to set.
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * @param createDate The createDate to set.
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * @param createdBy The createdBy to set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @param lastUpdate The lastUpdate to set.
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @param lastUpdatedBy The lastUpdatedBy to set.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * @param countryID The countryID to set.
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * This method overrides the toString method so that the division string is returned instead of the default.
     *
     * @return A string representing the division name.
     */
    @Override
    public String toString() {
        return division;
    }

    /**
     * The getAllFirstLevelByCountry method creates an observable list of FirstLevelDivision objects. It then searches
     * through a list of all the first level divisions in the database and checks to see if the countryID matches the
     * country parameter's countryID. If a match is found it is added to the list.
     *
     *
     * @param country The country whose countryID must be matched.
     * @param allFirstLevel An observable list of all first level divisions in the database.
     * @return A list of FirstLevelDivision objects whose countryID matches the country parameter's countryID.
     */
    public static ObservableList<FirstLevelDivision> getAllFirstLevelByCountry(Countries country, ObservableList<FirstLevelDivision> allFirstLevel) {
        ObservableList<FirstLevelDivision> divisionsByCountry = FXCollections.observableArrayList();
            for (FirstLevelDivision f : allFirstLevel){
                if (f.countryID == country.getCountryID()){
                    divisionsByCountry.add(f);
                }
            }
        return divisionsByCountry;
    }

    /**
     * The getAllFirstLevelByCustomer creates an observable list of FirstLevelDivision objects. It then searches through a list of all first
     * level divisions in the database for a first level division whose divisionID matches the divisionID of the customer parameter. When a match
     * is found, it searches a list of all countries in the database for a country whose countryID matches the countryID of the found first level division.
     * This matching countryID is saved to an integer. From there, the method again searches the list of all first level divisions, this time searching
     * for a first level division whose countryID matches the saved integer. If a match is found, the first level division is added to the new list.
     *
     * @param customer The customer whose divisionID must be matched.
     * @param allFirstLevel An observable list of all first level divisions in the database.
     * @param allCountries An observable list of all countries in the database.
     * @return A list of FirstLevelDivision objects whose divisionID matches the customer parameter's divisionID.
     */
    public static ObservableList<FirstLevelDivision> getAllFirstLevelByCustomer(Customer customer, ObservableList<FirstLevelDivision> allFirstLevel, ObservableList<Countries> allCountries){
        int countryIDToFind = 0;
        ObservableList<FirstLevelDivision> divisionsByCountry = FXCollections.observableArrayList();

        for (FirstLevelDivision f : allFirstLevel) {
            if (customer.getDivisionID() == f.divisionID) {
                for (Countries c : allCountries) {
                    if (f.countryID == c.getCountryID()) {
                        countryIDToFind = c.getCountryID();
                        break;
                    }
                }
            }
        }
        if (countryIDToFind >= 1){
            for (FirstLevelDivision f : allFirstLevel){
                if (f.countryID == countryIDToFind){
                    divisionsByCountry.add(f);
                }
            }
        }
        return divisionsByCountry;
    }

    /**
     * The getCountryName method searches through a list of all first level divisions in the database for a first level division whose divisionID
     * matches the customer parameter's divisionID. When a match is found, the method searches through a list of all countries in the database for
     * a country whose countryID matches the found first level division's countryID. It then returns the matching country.
     *
     * @param customer The customer whose divisionID must be matched.
     * @param allFirstLevel An observable list of all first level divisions in the database.
     * @param allCountries An observable list of all countries in the database.
     * @return The country whose countryID matches the customer parameter's divisionID via the first level division that matches the customer's divisionID.
     */
    public static Countries getCountryName(Customer customer, ObservableList<FirstLevelDivision> allFirstLevel, ObservableList<Countries> allCountries){
        Countries toReturn = null;
        for (FirstLevelDivision f : allFirstLevel) {
            if (customer.getDivisionID() == f.divisionID) {
                for (Countries c : allCountries) {
                    if (f.countryID == c.getCountryID()) {
                        toReturn = c;
                        break;
                    }
                }
            }
        }
        return toReturn;
    }

    /**
     * The getFirstLevelName method searches through a list of all first level divisions in the database for a first level
     * division whose divisionID matches the customer parameter's divisionID. If a match is found, the first level division
     * is returned.
     *
     * @param customer The customer whose divisionID must be matched.
     * @param allFirstLevel An observable list of all first level divisions in the database.
     * @return The first level division whose divisionID matches the customer parameter's divisionID.
     */
    public static FirstLevelDivision getFirstLevelName(Customer customer, ObservableList<FirstLevelDivision> allFirstLevel){
        FirstLevelDivision toReturn = null;
        for (FirstLevelDivision f : allFirstLevel){
            if(customer.getDivisionID() == f.divisionID){
                toReturn = f;
                break;
            }
        }return toReturn;
    }
}
