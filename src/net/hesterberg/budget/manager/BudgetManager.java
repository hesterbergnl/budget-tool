package net.hesterberg.budget.manager;

import net.hesterberg.budget.Date;
import net.hesterberg.budget.budget.Budget;
import net.hesterberg.budget.transaction.Purchase;
import net.hesterberg.budget.transaction.Transaction;
import net.hesterberg.budget.utility.BudgetFileIO;
import net.hesterberg.budget.utility.CategoryException;
import net.hesterberg.budget.utility.PurchaseFailureException;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.*;

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
     * Stores a list of the purchases that are displayed to the user
     */
    private ArrayList<Purchase> purchaseList;

    /**
     * Singleton instance of BudgetManager
     */
    private static BudgetManager budgetManager;

    /**
     * Stores whether the model is changed since last save
     */
    private boolean changed = false;

    /**
     * Constructor - builds the BudgetManager and initializes it
     * Private so it can't be freely built
     */
    private BudgetManager() {
        budget = new Budget();
    }

    /**
     * Saves the filename that is being used
     */
    private String filename = null;

    /**
     * Returns the copy of budget manager to be used by the GUI and other components
     */
    public static BudgetManager getBudgetManager() {
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
        changed = true;
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
    public void addPurchase(String day, String month, String year, String description, String cost, String category,
                            DefaultListModel purchaseListModel, DefaultListModel budgetListModel) throws PurchaseFailureException {
        Date date;
        int intCost;
        Purchase newPurchase;

        try {
            date = validateDate(day, month, year);
            intCost = validateCost(cost);
        } catch (IllegalArgumentException iae) {
            throw new PurchaseFailureException(iae.getMessage());
        }

        newPurchase = new Purchase(date, description, intCost, category, false);

        try {
            this.budget.addPurchase(newPurchase);
        } catch (CategoryException ce) {
            throw new PurchaseFailureException(ce.getMessage());
        }

        purchaseListModel.addElement(newPurchase);
        updateBudgetListModel(budgetListModel);
        changed = true;
    }

    /**
     * Updates the budget List model based on the budget categories and the amount left in each category
     *
     * @param budgetListModel - model to be updated
     */
    private void updateBudgetListModel(DefaultListModel budgetListModel) {
        StringBuilder str;
        Map.Entry amtEntry;
        Map.Entry budgetEntry;
        String category;
        int remaining;
        double remainingDbl;
        int total;
        double totalDbl;

        budgetListModel.clear();

        Iterator<Map.Entry<String, Integer>> budgetSpent = budget.getCategoryTotalSpent().entrySet().iterator();
        Iterator<Map.Entry<String, Integer>> budgetAmounts= budget.getBudget().entrySet().iterator();

        while(budgetAmounts.hasNext()) {
            budgetEntry = budgetAmounts.next();
            amtEntry = budgetSpent.next();
            category = (String) budgetEntry.getKey();

            total = (int) budgetEntry.getValue();
            remaining = total - (int) amtEntry.getValue();

            totalDbl = (double)total / 100;
            remainingDbl = (double)remaining / 100;

            str = new StringBuilder();

            str.append(category);
            str.append(" | $");
            str.append(String.format ("%.2f", totalDbl));
            str.append(" | $");
            str.append(String.format ("%.2f", remainingDbl));

            budgetListModel.addElement(str.toString());

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

    /**
     * Clear budget - clears the budget and starts it from scratch
     */
    public void clearBudget(DefaultListModel budgetListModel, DefaultListModel purchaseListModel) {
        budget = new Budget(budget.getTotalBudget());
        updateBudgetListModel(budgetListModel);
        updatePurchaseListModel(purchaseListModel);
        changed = false;
    }

    /**
     * Delete purchase - removes the purchase provided from the budget
     *
     * @param index - index in the purchaseList of the purchase
     */
    public void deletePurchase(int index, DefaultListModel purchaseListModel) {
        Purchase removePurchase = (Purchase) purchaseListModel.get(index);

        budget.removeTransaction(removePurchase);

        purchaseListModel.remove(index);
        changed = true;
    }

    /**
     * Returns the list of purchases to be shown on the GUI - depends on the filter and sorting view
     */
    public ArrayList<Purchase> getPurchaseList() {
        return this.purchaseList;
    }

    /**
     * Removes the category from the budget
     *
     * @param index - category to remove from the budget
     * @param budgetListModel - list of budget items that is displayed to the user
     */
    public void removeCategory(int index, DefaultListModel budgetListModel, DefaultListModel purchaseListModel) {
        String categoryEntry = (String) budgetListModel.get(index);
        String[] tokens = null;
        tokens = categoryEntry.split(" \\| ");

        String category = tokens[0];

        budget.removeCategory(category);
        updateBudgetListModel(budgetListModel);
        updatePurchaseListModel(purchaseListModel);
        changed = true;
    }

    /**
     * Updates the purchase list model based on the purchases
     *
     * @param purchaseListModel - model to update
     */
    private void updatePurchaseListModel(DefaultListModel purchaseListModel) {
        purchaseListModel.clear();
        for(Transaction purchase: budget.getPurchaseList()) {
            purchaseListModel.addElement(purchase);
        }
    }

    /**
     * Adds a category to the budget
     *
     * @param budgetName - name of the budget category
     * @param amount - amount of the budget category
     * @throws IllegalArgumentException if the budget string is formatted incorrectly or category exists
     */
    public void addCategory(String budgetName, String amount, DefaultListModel budgetListModel)
            throws IllegalArgumentException {

        int budgetAmount = validateCost(amount);

        budget.addBudgetBucket(budgetName, budgetAmount);

        updateBudgetListModel(budgetListModel);
        changed = true;
    }

    /**
     * Returns the filename that is being used
     *
     * @return filename stored
     */
    public String getFilename() {
        return this.filename;
    }

    /**
     * Sets the default filename stored in the manager instance
     *
     * @param filename - default filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Returns whether the state has changed since last save
     *
     * @return changed value
     */
    public boolean isChanged() {
        return this.changed;
    }


    //TODO: Make a class to load and save text files for offline use
    /**
     * Saves the budget at the given filename
     *
     * @param filename - file to save the budget to
     */
    public void saveBudget(String filename) {
        BudgetFileIO.SaveBudgetFile(filename, budget);
        changed = false;
    }

    /**
     * Loads a budget from the given filename
     *
     * @param filename - file to load the budget from
     */
    public void loadBudget(String filename, DefaultListModel budgetListModel, DefaultListModel purchaseListModel) {
        budget = BudgetFileIO.LoadBudgetFile(filename);
        changed = false;
        updateBudgetListModel(budgetListModel);
        updatePurchaseListModel(purchaseListModel);
    }
}
