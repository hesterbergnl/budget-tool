package net.hesterberg.budget;

import java.util.Objects;

/**
 * Custom date class - used to create date objects for date stamping items
 * Contains a basic static array with month abbreviations for pretty String printing
 * Stores the year, month, and day
 * Implements Comparable interface for sorting
 */
public class Date implements Comparable<Date> {
    private int day;
    private int month;
    private int year;
    private static String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct",
            "Nov", "Dec"};

    /**
     * Date constructor - calls input validation method and sets the internal parameters.
     *
     * @param day - day of the date
     * @param month - month of the date
     * @param year - year of the date
     */
    public Date(int day, int month, int year) {
        validateInputs(day, month, year);
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * Retreives the day from private variables
     * @return day of the date
     */
    public int getDay() {
        return day;
    }

    /**
     * Retreives the month from private variables
     * @return month of the date
     */
    public int getMonth() {
        return month;
    }

    /**
     * Retreives the year from private variables
     * @return year of the date
     */
    public int getYear() {
        return year;
    }

    /**
     * Overrides the toString method for a nice date presentation
     * Uses the months static array to return an abbreviated month
     *
     * @return date as a string with month abbreviation
     */
    @Override
    public String toString() {
        return day + "-" + months[month - 1] + "-" + year;
    }

    /**
     * Compares the date object to another date object.
     * Starts with Year comparision then month then day.
     *
     * @param other - date object to compare to
     * @return negative if this object is less than comparable object, positive if more, and 0 if equal
     * @throws NullPointerException if the comparable object is null
     */
    @Override
    public int compareTo(Date other) {
        if(other == null) {
            throw new NullPointerException();
        }
        if(other.getYear() > this.year) {
            return -1;
        }
        else if(other.getYear() < this.year) {
            return 1;
        }
        else if(other.getMonth() > this.month) {
            return -1;
        }
        else if(other.getMonth() < this.month) {
            return 1;
        }
        else if(other.getDay() > this.day) {
            return -1;
        }
        else if(other.getDay() < this.day) {
            return 1;
        }
        else {
            return 0;
        }
    }

    /**
     * equals method override
     * Compares this to object o for equality
     * If the objects are the same, they are equal
     * If objects are not same type, they are not equal
     * If objects are same type and have equal year, month, and day - they are equal
     *
     * @param o - object to compare to
     * @return true if objects are equal, false if not
     */
    @Override
    public boolean equals(Object o) {
        //check if the object is itself
        if(this == o) {
            return true;
        }

        //if o is not an instance of Date, they aren't equal
        if(!(o instanceof Date)) {
            return false;
        }

        //cast to Date object
        Date compare = (Date) o;

        //check if day, month, and year are equal
        //if yes, objects are the same
        if(compare.getYear() != this.year) {
            return false;
        }
        if(compare.getMonth() != this.month) {
            return false;
        }
        if(compare.getDay() != this.day) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * hashCode override method
     * Generates hash code based on day, month, and year
     *
     * @return hashcode as an integer
     */
    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }

    //----------------HELPER METHODS---------------------------------//
    /**
     * Private helper method that validates the inputs
     * Will have inaccuracy for the day - not all months have consistent days/month
     *
     * @param day - day to validate between 1 and 31
     * @param month - month to validate between 1 and 12
     * @param year - year to validate greater than 0
     * @throws IllegalArgumentException if any input fails validation
     */
    private void validateInputs(int day, int month, int year) {
        //This isn't 100% accurate, but a good start on validation
        if(day < 1 || day > 31) {
            throw new IllegalArgumentException("Day must be between 1 and 31");
        }

        if(month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }

        if(year < 1) {
            throw new IllegalArgumentException("Year must be greater than 0");
        }
    }
}