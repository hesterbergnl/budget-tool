package net.hesterberg.budget.budget;

import net.hesterberg.budget.transaction.Transaction;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Budget class - The main class modelling the budget
 * Stores the overall budget amount
 * Stores the current spent amount
 * Stores a hash map containing <Category, Budget> for each method
 * Stores a hash map containing <Category, ArrayList<Purchases>>
 * All monetary amounts are stored in CENTS to avoid floating point precision errors
 *
 * @author Nikolai Hesterberg
 */
public class Budget {
    /**
     * Stores the total budgeted amount
     */
    private int totalBudget;

    /**
     * Stores the categories and budget for each category
     */
    private HashMap<String, Integer> budget;

    /**
     * Stores the categories and a list of purchases for each category
     */
    private HashMap<String, ArrayList<Transaction>> purchases;

    /**
     * Null constructor - builds a new budget with an initial total budget of $0
     */
    public Budget() {
        this(0);
    }

    /**
     * Parameterized constructor - builds a new budget with an initial amount of money
     *
     * @param totalBudget - initial budget amount to set
     */
    public Budget(int totalBudget) {
        this.totalBudget = totalBudget;
        this.budget = new HashMap<String, Integer>();
        this.purchases = new HashMap<String, ArrayList<Transaction>>();
    }

    /**
     * Returns the total budgeted amount in cents
     *
     * @return total budgeted amount in cents
     */
    public int getTotalBudget() {
        return totalBudget;
    }

    /**
     * Sets the total budget in cents
     *
     * @param totalBudget - the budget in cents
     */
    public void setTotalBudget(int totalBudget) {
        this.totalBudget = totalBudget;
    }

    /**
     * Adds (or updates) a new budget category
     * The category is added and the budget for the category set
     * Per hashMap functionality, it will update the categoryBudget if the category already exists
     *
     * @param bucket - the name for the budget category or bucket
     * @param categoryBudget - the dollar amount (in cents) for the bucket
     */
    public void addBudgetBucket(String bucket, int categoryBudget) {
        this.budget.put(bucket, categoryBudget);
    }
}
