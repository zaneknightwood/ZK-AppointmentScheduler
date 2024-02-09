package model;

import java.time.LocalDateTime;

/**
 * The Countries class defines an instance of Countries.
 */

public class Countries {

    private int countryID;
    private String country;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    public Countries(int countryID, String country, LocalDateTime createdDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.countryID = countryID;
        this.country = country;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * @return The countryID.
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * @return The country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * @return The createdDate.
     */
    public LocalDateTime getCreatedDate() {
        return createdDate;
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
     *
     * @param country The country to set.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @param countryID The countryID to set.
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     *
     * @param createdBy The createdBy to set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @param createdDate The createdDate to set.
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     *
     * @param lastUpdate The lastUpdate to set.
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @param lastUpdatedBy The lastUpdatedBy to set.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method overrides the toString method so that the country string is returned instead of the default.
     *
     * @return A string representing the country name.
     */
    @Override
    public String toString(){
        return (country);
    }
}


