package net.hesterberg.budget.transaction;

import net.hesterberg.budget.Date;

/**
 * Interface used to define the required methods of a purchase.
 * The methods will be shared for standard purchases and any other subtype such as reoccuring purchases
 *
 * @author Nikolai Hesterberg
 */
public interface Transaction {
    /**
     * Returns the date of the transaction
     *
     * @return date of the transaction
     */
    public Date getDate();

    /**
     * Returns the price of the transaction
     *
     * @return price of the transaction
     */
    public int getPrice();

    /**
     * Returns the description of the transaction
     *
     * @return description of the transaction
     */
    public String getDescription();

    /**
     * Returns the category of the transaction
     *
     * @return category of the transaction
     */
    public String getCategory();
}
