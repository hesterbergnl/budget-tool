package net.hesterberg.budget.transaction;

import net.hesterberg.budget.Date;

public class ReoccurringPurchase extends Purchase {
    /**
     * Enumerated constant defines the the 3 possible repetitive frequencies
     */
    public enum Repeats {WEEKLY, MONTHLY, YEARLY};

    private Repeats repeats;

    /**
     * Builds a new purchase that stores all details of a transaction
     *
     * @param date - date of the purchase
     * @param description - description of the purchase
     * @param price - price of the purchase
     * @param category - category of the purchase
     * @param reoccurring - whether the transaction will be reoccuring or not
     * @param repeats - stores if the transaction repeats weekly, monthly, or yearly
     */
    public ReoccurringPurchase(Date date, String description, int price, String category, boolean reoccurring,
                                    Repeats repeats) {
        super(date, description, price, category, reoccurring);
        this.repeats = repeats;
    }

    /**
     * Returns the enumerated constant for when the purchase repeats
     *
     * @return weekly, monthly, or yearly
     */
    public Repeats getRepeats() {
        return repeats;
    }

    /**
     * Sets the frequency that the purchase repeats
     *
     * @param repeats - the frequency that the purchase will repeat
     */
    public void setRepeats(Repeats repeats) {
        this.repeats = repeats;
    }
}
