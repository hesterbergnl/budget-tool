package net.hesterberg.budget.budget;

import net.hesterberg.budget.transaction.Purchase;
import net.hesterberg.budget.transaction.Transaction;
import net.hesterberg.budget.utility.CategoryException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
        if(this.budget.get(bucket) == null) {
            this.budget.put(bucket, categoryBudget);
        }
        else {
            throw new IllegalArgumentException("Budget already exists");
        }
    }

    /**
     * Returns the budget hashmap that contains the budget categories and their dollar limit
     *
     * @return HashMap containing the budget categories and assigned value
     */
    public HashMap<String, Integer> getBudget() {
        return this.budget;
    }

    /**
     * Returns the total amount spent in each budget category
     * Loops through each category and then loops through each list of purchases
     * May need to store this dynamically in the future for faster lookups
     *
     * @return map with category and total budget for that category
     */
    public HashMap<String, Integer> getCategoryTotalSpent() {
        ArrayList<Transaction> txs;
        HashMap<String, Integer> categoryTotals = new HashMap<String, Integer>();
        Integer total = 0;

        for(Map.Entry<String, ArrayList<Transaction>> entry: purchases.entrySet()) {
            txs = entry.getValue();
            total = 0;
            for(Transaction tx: txs) {
                total += tx.getPrice();
            }
            categoryTotals.put(entry.getKey(), total);
        }

        //Loops through and sets the category spent to $0 for any category without purchases
        for(Map.Entry<String, Integer> entry: budget.entrySet()) {
            if(categoryTotals.get(entry.getKey()) == null) {
                categoryTotals.put(entry.getKey(), 0);
            }
        }

        return categoryTotals;
    }

    /**
     * Adds a new purchase to the purchases map
     * Throws a Category Exception if the category doesn't currently exist in the budget
     *
     * @param purchase - purchase to add to the map
     * @throws CategoryException if category doesn't exist in the budget
     */
    public void addPurchase(Transaction purchase) throws CategoryException {
        String category = purchase.getCategory();

        Integer categoryTotal = budget.get(category);

        if(categoryTotal == null) {
            throw new CategoryException(category + " does not currently exist in the budget!");
        }

        //Retrieve the purchase list from the purchases map
        ArrayList<Transaction> p = purchases.get(category);

        if(p == null) {
            p = new ArrayList<Transaction>();
        }
        p.add(purchase);

        purchases.put(category, p);
    }

    /**
     * Returns the total amount of money spent thus far
     * Need to update this to be dynamically stored later
     *
     * @return the total amount spent
     */
    public int getBudgetTotalSpent() {
        Map<String, Integer> categoryTotals = getCategoryTotalSpent();
        int total = 0;

        for(Map.Entry<String, Integer> cat: categoryTotals.entrySet()) {
            total += cat.getValue();
        }

        return total;
    }

    /**
     * Removes a purchase from the purchases list
     * Returns the description of the purchase that was removed
     * Returns null if the purchase didn't exist in the purchases list
     *
     * @param purchase - purchase that needs to be removed from the list
     * @return description of the removed purchase
     */
    public String removeTransaction(Transaction purchase) {
        String category = purchase.getCategory();
        String description = purchase.getDescription();
        ArrayList<Transaction> txs = purchases.get(category);

        boolean removed = txs.remove(purchase);

        if(removed) {
            return description;
        }

        return null;
    }

    /**
     * Removes a category from the budget
     *
     * @param category - category to be removed
     */
    public void removeCategory(String category) {
        budget.remove(category);

        purchases.remove(category);
    }

    /**
     * Returns the list of purchases
     *
     * @return list of purchases
     */
    public ArrayList<Purchase> getPurchaseList() {
        ArrayList<Purchase> purchaseList = new ArrayList<>();

        for(String key: purchases.keySet()) {
            ArrayList<Transaction> pList = purchases.get(key);
            for(int i = 0; i < pList.size(); i++) {
                purchaseList.add((Purchase)(pList.get(i)));
            }
        }

        Collections.sort(purchaseList);

        return purchaseList;
    }
}