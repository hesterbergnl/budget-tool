package net.hesterberg.budget.transaction;

import net.hesterberg.budget.Date;

public class Purchase implements Transaction, Comparable<Purchase> {
    private Date date;
    private String description;
    private int price;
    private String category;
    private boolean reoccurring;

    /**
     * Builds a new purchase that stores all details of a transaction
     *
     * @param date - date of the purchase
     * @param description - description of the purchase
     * @param price - price of the purchase
     * @param category - category of the purchase
     * @param reoccurring - whether the transaction will be reoccuring or not
     */
    public Purchase(Date date, String description, int price, String category, boolean reoccurring) {
        this.date = date;
        this.description = description;
        this.price = price;
        this.category = category;
        this.reoccurring = reoccurring;
    }

    /**
     * Returns the date of the purchase
     *
     * @return date of the purchase
     */
    @Override
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of the purchase
     *
     * @param date - sets the date of the purchase
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns the description of the purchase
     *
     * @return description of the purchase
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the purchase
     *
     * @param description - description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * returns the price of the purchase
     *
     * @return price of the purchase
     */
    @Override
    public int getPrice() {
        return price;
    }

    /**
     * Sets the price of the purchase
     *
     * @param price - price to set
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Returns the category of the purchase
     *
     * @return category of the purchase
     */
    @Override
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of the purchase
     *
     * @param category - category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Compares purchase o to this purchase
     *
     * @param o - other purchase to compare with
     * @return -1 if this less than o, 1 if greater than o, and 0 if equal
     */
    @Override
    public int compareTo(Purchase o) {
        if(this.price > o.getPrice()) {
            return 1;
        }
        else if(this.price < o.getPrice()) {
            return -1;
        }
        else {
            if(this.description.compareTo(o.getDescription()) > 0) {
                return 1;
            }
            else if(this.description.compareTo(o.getDescription()) < 0) {
                return -1;
            }
            else {
                return 0;
            }
        }
    }
}