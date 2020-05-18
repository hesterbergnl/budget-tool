package net.hesterberg.budget.manager;

import net.hesterberg.budget.Date;
import net.hesterberg.budget.budget.Budget;
import net.hesterberg.budget.transaction.Purchase;
import net.hesterberg.budget.utility.CategoryException;
import net.hesterberg.budget.utility.PurchaseFailureException;

/**
 * Budget manager stores the budget and makes it accessible to the GUI
 * Uses the singleton design pattern to ensure only one instance of itself can be created
 *
 * @author Nikolai Hesterberg
 */
public class BudgetManager {
    /**
     * The main budget class that models the overall system
     */
    private Budget budget;

    /**
     * Singleton instance of BudgetManager
     */
    private BudgetManager budgetManager;

    /**
     * Constructor - builds the BudgetManager and initializes it
     * Private so it can't be freely built
     */
    private BudgetManager() {
        budget = new Budget();
    }

    /**
     * Returns the copy of budget manager to be used by the GUI and other components
     */
    public BudgetManager getBudgetManager() {
        if(budgetManager == null) {
            budgetManager = new BudgetManager();
        }

        return budgetManager;
    }

    /**
     * Sets the overall budget amount
     *
     * @param budget - budget to set
     */
    public void setBudget(int budget) {
        this.budget.setTotalBudget(budget);
    }

    /**
     * Adds a new purchase to the budget
     * Takes all string parameters so it can validate the user inputs as strings
     * Passes the exception up to the caller using a String with an error message
     *
     * @param day - day of the purchase as a string
     * @param month - month of the purchase as a string
     * @param year - year of the purchase as a string
     * @param description - description of the purchase
     * @param cost - cost of the purchase
     * @param category - category of the purchase
     * @throws PurchaseFailureException if there is a casting error or category exception
     */
    public void addPurchase(String day, String month, String year, String description, String cost, String category)
                                    throws PurchaseFailureException {
        Date date;
        int intCost;

        try {
            date = validateDate(day, month, year);
            intCost = validateCost(cost);
        } catch (IllegalArgumentException iae) {
            throw new PurchaseFailureException(iae.getMessage());
        }

        try {
            this.budget.addPurchase(new Purchase(date, description, intCost, category, false));
        } catch (CategoryException ce) {
            throw new PurchaseFailureException(ce.getMessage());
        }
    }

    /**
     * Validates the cost input
     * Input is a double formatted as a String
     * Casts to a double then coverts to an integer
     * Budget costs stored as ints for precision
     *
     * @param cost - the cost as a String representing a double
     * @return integer representation of the cost
     */
    private int validateCost(String cost) throws IllegalArgumentException {
        double dblCost;
        int intCost;

        try {
            dblCost = Double.parseDouble(cost);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Cost is not a valid number!");
        }

        //TODO: Figure out a way to make this more exact and not have rounding errors
        intCost = (int) (dblCost * 100);

        return intCost;
    }

    /**
     * Validates the date to ensure it can be created
     * Casts from string to an int
     * Returns a valid date
     *
     * @param day - day as a String
     * @param month - month as a String
     * @param year - year as a String
     * @return valid date object
     * @throws IllegalArgumentException if the date cannot be casted
     */
    private Date validateDate(String day, String month, String year) {
        int intDay, intMonth, intYear;
        Date date;

        try {
            intDay = Integer.parseInt(day);
            intMonth = Integer.parseInt(month);
            intYear = Integer.parseInt(year);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Date cannot be casted to integers");
        }

        date = new Date(intDay, intMonth, intYear);
        return date;
    }

    //TODO: Make a class to load and save text files for offline use
    public void saveBudget(String filename) {

    }

    public void loadBudget(String filename) {

    }
}
