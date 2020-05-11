package net.hesterberg.budget.budget;

import net.hesterberg.budget.Date;
import net.hesterberg.budget.transaction.Purchase;
import net.hesterberg.budget.transaction.Transaction;
import net.hesterberg.budget.utility.CategoryException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Test the budget class for full functionality
 */
public class BudgetTest {
    Budget budget;
    Transaction tx1, tx2, tx3, tx4, tx5;

    /**
     * Builds a new budget and creates predefined transactions that are used in the tests
     * Runs before each test method is run
     */
    @Before
    public void setUp() {
        tx1 = new Purchase(new Date(5, 1, 2020), "Purchase 1", 5000, "Category 1", false);
        tx2 = new Purchase(new Date(6, 1, 2020), "Purchase 2", 5000, "Category 1", false);
        tx3 = new Purchase(new Date(7, 1, 2020), "Purchase 3", 5000, "Category 2", false);
        tx4 = new Purchase(new Date(8, 1, 2020), "Purchase 4", 5000, "Category 2", false);
        tx5 = new Purchase(new Date(9, 1, 2020), "Purchase 5", 5000, "Category 3", false);

        budget = new Budget();
    }

    /**
     * Tests the get and set total budget methods
     * Sets up the budget with a fixed amount and tests the it changes
     */
    @Test
    public void setTotalBudget() {
        assertEquals(0, budget.getTotalBudget());
        budget.setTotalBudget(50000);
        assertEquals(50000, budget.getTotalBudget());
    }

    /**
     * Tests adding a budget bucket to the budget
     * Adds the budget bucket then checks to see if it was successfully added
     */
    @Test
    public void addBudgetBucket() {
        budget.addBudgetBucket("Category 1", 5000);
        assertEquals(1, budget.getBudget().size());
        budget.addBudgetBucket("Category 2", 10000);
        assertEquals(2, budget.getBudget().size());

        //Returns the hashmap containing the budget and checks category totals
        HashMap<String, Integer> budgetMap = budget.getBudget();
        assertEquals(5000, budgetMap.get("Category 1").intValue());
        assertEquals(10000, budgetMap.get("Category 2").intValue());
    }

    /**
     * Tests the functionality of the get category total spent method
     * Adds categories to the budget the adds transactions and checks computation of total category spending
     * @throws CategoryException if the category doesn't exist when adding a transaction
     */
    @Test
    public void getCategoryTotalSpent() throws CategoryException {
        budget.addBudgetBucket("Category 1", 15000);
        budget.addPurchase(tx1);
        budget.addPurchase(tx2);
        budget.addBudgetBucket("Category 2", 10000);
        budget.addPurchase(tx3);
        HashMap<String, Integer> categorySpending = budget.getCategoryTotalSpent();
        assertEquals(10000, categorySpending.get("Category 1").intValue());
        assertEquals(5000, categorySpending.get("Category 2").intValue());
    }

    /**
     * Finish testing the addPurchase method - functionality of throwing custom exception CategoryException
     *
     * @throws CategoryException when a purchase is added that doesn't have a corresponding category
     */
    @Test
    public void addPurchase() {
        try {
            budget.addPurchase(tx1);
        } catch (CategoryException ce) {
            assertEquals("Category 1 does not currently exist in the budget!", ce.getMessage());
        }
    }

    /**
     * Tests the get total budget spent method - calculates the total amount spend in the budget so far
     * Tests by creating a budget with two categories, adding purchases, and checking budget total spent after each add
     *
     * @throws CategoryException if
     */
    @Test
    public void getBudgetTotalSpent() throws CategoryException {
        budget.addBudgetBucket("Category 1", 15000);
        assertEquals(0, budget.getBudgetTotalSpent());
        budget.addPurchase(tx1);
        assertEquals(5000, budget.getBudgetTotalSpent());
        budget.addPurchase(tx2);
        assertEquals(10000, budget.getBudgetTotalSpent());
        budget.addBudgetBucket("Category 2", 10000);
        budget.addPurchase(tx3);
        assertEquals(15000, budget.getBudgetTotalSpent());
    }

    /**
     * Tests the remove transaction method
     * Adds purchases to the budget then removes them and checks for successful removal
     */
    @Test
    public void removeTransaction() throws CategoryException {
        budget.addBudgetBucket("Category 1", 15000);
        budget.addPurchase(tx1);
        budget.addPurchase(tx2);
        budget.addBudgetBucket("Category 2", 10000);
        budget.addPurchase(tx3);
        assertEquals(15000, budget.getBudgetTotalSpent());

        budget.removeTransaction(tx1);
        assertEquals(10000, budget.getBudgetTotalSpent());

        budget.removeTransaction(tx2);
        assertEquals(5000, budget.getBudgetTotalSpent());

        budget.removeTransaction(tx3);
        assertEquals(0, budget.getBudgetTotalSpent());
    }
}