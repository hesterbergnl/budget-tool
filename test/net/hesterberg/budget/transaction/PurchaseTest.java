package net.hesterberg.budget.transaction;

import net.hesterberg.budget.Date;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test the purchase class
 *
 * @author Nikolai Hesterberg
 */
public class PurchaseTest {
    Purchase purchase;
    Date date;
    Date newDate;

    /**
     * Sets up the parameters used for each test
     *
     * @throws Exception - if date fails to construct
     */
    @Before
    public void setUp() throws Exception {
        date = new Date(30, 5, 2020);
        newDate = new Date(15, 3, 2020);
        purchase = new Purchase(date, "Gas", 5000, "Testing", false);
    }

    /**
     * Tests the set date and get date method by changing the date and checking that it changed
     */
    @Test
    public void setDate() {
        assertEquals(date, purchase.getDate());
        purchase.setDate(newDate);
        assertEquals(newDate, purchase.getDate());
        purchase.setDate(date);
        assertEquals(date, purchase.getDate());
    }

    /**
     * Tests the set description and set description methods by changing the description and checking that it changed
     */
    @Test
    public void setDescription() {
        assertEquals("Gas", purchase.getDescription());
        purchase.setDescription("Pizza");
        assertEquals("Pizza", purchase.getDescription());
        purchase.setDescription(("Gas"));
        assertEquals("Gas", purchase.getDescription());
    }

    /**
     * Tests the get price and set price methods by changing the price and checking that it changed
     */
    @Test
    public void setPrice() {
        assertEquals(5000, purchase.getPrice());
        purchase.setPrice(50);
        assertEquals(50, purchase.getPrice());
        purchase.setPrice(5000);
        assertEquals(5000, purchase.getPrice());
    }

    /**
     * Tests the get category and set category methods by changing the category and checking that it changed
     */
    @Test
    public void setCategory() {
        assertEquals("Testing", purchase.getCategory());
        purchase.setCategory("Not Testing");
        assertEquals("Not Testing", purchase.getCategory());
        purchase.setCategory("Testing");
        assertEquals("Testing", purchase.getCategory());
    }

    /**
     * Tests the compareTo method
     *
     * Testing is done by building a second object and testing every part of the nested if-else statements
     */
    @Test
    public void compareTo() {
        Purchase purchase2 = new Purchase(new Date(15, 2, 2020), "Gas", 4000, "Testing", false);
        assertEquals(1, purchase.compareTo(purchase2));
        assertEquals(-1, purchase2.compareTo(purchase));
        assertEquals(0, purchase.compareTo(purchase));

        purchase2.setPrice(6000);
        assertEquals(-1, purchase.compareTo(purchase2));
        assertEquals(1, purchase2.compareTo(purchase));

        purchase2.setPrice(5000);
        purchase2.setDescription("Bananas");
        assertEquals(1, purchase.compareTo(purchase2));
        assertEquals(-1, purchase2.compareTo(purchase));

        purchase2.setDescription("Ice");
        assertEquals(-1, purchase.compareTo(purchase2));
        assertEquals(1, purchase2.compareTo(purchase));

        purchase2.setDescription("Gas");
        assertEquals(0, purchase.compareTo(purchase2));
        assertEquals(0, purchase2.compareTo(purchase));
    }
}