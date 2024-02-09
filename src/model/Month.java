package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Month class defines an instance of Month.
 */

public class Month {

    private String name;
    private int number;

    private static ObservableList<Month> allMonths = FXCollections.observableArrayList();

    /** @return An observable list of all months. */
    public static ObservableList<Month> getAllMonths() {return allMonths;}

    public Month(int number, String name){
        this.name = name;
        this.number = number;
    }

    /**
     * @return The month name.
     */
    public String getName() {return name;}

    /**
     * @return The month number.
     */
    public int getNumber() {return number;}

    /**
     * The setUpMonths method creates a new instance of Month for each month of a calendar year.
     */
    public static void setUpMonths() {
        Month jan = new Month(1, "January");
        Month feb = new Month(2, "February");
        Month mar = new Month(3, "March");
        Month apr = new Month(4, "April");
        Month may = new Month(5, "May");
        Month jun = new Month(6, "June");
        Month jul = new Month(7, "July");
        Month aug = new Month(8, "August");
        Month sep = new Month(9, "September");
        Month oct = new Month(10, "October");
        Month nov = new Month(11, "November");
        Month dec = new Month(12, "December");

        allMonths.add(jan);
        allMonths.add(feb);
        allMonths.add(mar);
        allMonths.add(apr);
        allMonths.add(may);
        allMonths.add(jun);
        allMonths.add(jul);
        allMonths.add(aug);
        allMonths.add(sep);
        allMonths.add(oct);
        allMonths.add(nov);
        allMonths.add(dec);
    }

    /**
     * This method overrides the toString method so that the name string is returned instead of the default.
     *
     * @return A string representing the month name.
     */
    @Override
    public String toString(){ return name;}
}
