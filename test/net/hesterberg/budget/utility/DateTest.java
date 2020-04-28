package net.hesterberg.budget;
import net.hesterberg.budget.Date;
import org.junit.jupiter.api.Test;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class DateTest {

    /**
     * Test the constructor method and the helper data validation method
     */
    @Test
    void testDate() {
        Date date;

        //test lower fail bound for day input
        try {
            date = new Date(0, 1, 2020);
            fail("Should not allow 0 day");
        } catch (IllegalArgumentException e) {
        }

        //test upper fail bound for day input
        try {
            date = new Date(32, 1, 2020);
            fail("Should not allow 32 day");
        } catch (IllegalArgumentException e) {
        }

        //test lower fail bound for month input
        try {
            date = new Date(1, 0, 2020);
            fail("Should not allow 0 month");
        } catch (IllegalArgumentException e) {
        }

        //test upper fail bound for month input
        try {
            date = new Date(1, 13, 2020);
            fail("Should not allow 13 month");
        } catch (IllegalArgumentException e) {
        }

        //test lower fail bound for year input
        try {
            date = new Date(1, 1, 0);
            fail("Should not allow 0 year");
        } catch (IllegalArgumentException e) {
        }

        //test a good constructor
        date = new Date(1, 1, 2020);
        assertNotNull(date);
    }

    /**
     * Test the getter method for day
     */
    @Test
    void getDay() {
        Date date;

        date = new Date(20, 1, 2020);
        assertEquals(20, date.getDay());
    }

    /**
     * Test the getter method for month
     */
    @Test
    void getMonth() {
        Date date;

        date = new Date(20, 1, 2020);
        assertEquals(1, date.getMonth());
    }

    /**
     * Test the getter method for year
     */
    @Test
    void getYear() {
        Date date;

        date = new Date(20, 1, 2020);
        assertEquals(2020, date.getYear());
    }

    /**
     * Test the toString method
     */
    @Test
    void testToString() {
        //Test each month abbreviation
        Date date = new Date(20, 1, 2020);
        assertEquals("20-Jan-2020", date.toString());

        date = new Date(20, 2, 2020);
        assertEquals("20-Feb-2020", date.toString());

        date = new Date(20, 3, 2020);
        assertEquals("20-Mar-2020", date.toString());

        date = new Date(20, 4, 2020);
        assertEquals("20-Apr-2020", date.toString());

        date = new Date(20, 5, 2020);
        assertEquals("20-May-2020", date.toString());

        date = new Date(20, 6, 2020);
        assertEquals("20-Jun-2020", date.toString());

        date = new Date(20, 7, 2020);
        assertEquals("20-Jul-2020", date.toString());

        date = new Date(20, 8, 2020);
        assertEquals("20-Aug-2020", date.toString());

        date = new Date(20, 9, 2020);
        assertEquals("20-Sep-2020", date.toString());

        date = new Date(20, 10, 2020);
        assertEquals("20-Oct-2020", date.toString());

        date = new Date(20, 11, 2020);
        assertEquals("20-Nov-2020", date.toString());

        date = new Date(20, 12, 2020);
        assertEquals("20-Dec-2020", date.toString());
    }

    /**
     * Test the comparison method
     */
    @Test
    void compareTo() {
        Date date1;
        Date date2;

        //Keep date1 the same and change date2 for comparison
        //check backwards compatibility
        date1 = new Date(15, 6, 2019);

        //check the year comparison
        //date2 before date1
        date2 = new Date(15, 6, 2018);
        assertEquals(1, date1.compareTo(date2));
        assertEquals(-1, date2.compareTo(date1));

        //date2 after date1
        date2 = new Date(15, 6, 2020);
        assertEquals(-1, date1.compareTo(date2));
        assertEquals(1, date2.compareTo(date1));

        //check month comparison
        //date2 before date1
        date2 = new Date(15, 5, 2019);
        assertEquals(1, date1.compareTo(date2));
        assertEquals(-1, date2.compareTo(date1));

        //date2 after date1
        date2 = new Date(15, 7, 2019);
        assertEquals(-1, date1.compareTo(date2));
        assertEquals(1, date2.compareTo(date1));

        //check day comparison
        //date2 before date1
        date2 = new Date(14, 6, 2019);
        assertEquals(1, date1.compareTo(date2));
        assertEquals(-1, date2.compareTo(date1));

        //date2 after date1
        date2 = new Date(16, 6, 2019);
        assertEquals(-1, date1.compareTo(date2));
        assertEquals(1, date2.compareTo(date1));

        //test date1 == date2
        date2 = new Date(15, 6, 2019);
        assertEquals(0, date1.compareTo(date2));
        assertEquals(0, date2.compareTo(date1));
    }

    /**
     * Test the equals method
     */
    @Test
    void equals() {
        Date date1;
        Date date2;

        date1 = new Date(15, 6, 2019);
        date2 = new Date(15, 6, 2018);

        //check for equality with same object
        assertTrue(date1.equals(date1));
        assertTrue(date2.equals(date2));

        //check for equality with different object type
        Object pt = new Point();
        assertFalse(date1.equals(pt));
        assertFalse(date2.equals(pt));

        //Check for equality with different year
        date2 = new Date(15, 6, 2018);
        assertFalse(date1.equals(date2));
        assertFalse(date2.equals(date1));

        //Check for equality with different month
        date2 = new Date(15, 7, 2019);
        assertFalse(date1.equals(date2));
        assertFalse(date2.equals(date1));

        //Check for equality with different day
        date2 = new Date(14, 6, 2019);
        assertFalse(date1.equals(date2));
        assertFalse(date2.equals(date1));

        //Check for equality with same year, month, and day
        date2 = new Date(15, 6, 2019);
        assertTrue(date1.equals(date2));
        assertTrue(date2.equals(date1));
    }

    /**
     * Test the hashCode method
     */
    @Test
    void hashCodeTest() {
        Date date1;
        Date date2;

        date1 = new Date(15, 6, 2019);
        date2 = new Date(15, 6, 2018);

        //check equality to same object's hashCode
        assertEquals(date1.hashCode(), date1.hashCode());
        assertEquals(date2.hashCode(), date2.hashCode());

        //Check date1 and date2 hashCode not equal
        assertNotEquals(date1.hashCode(), date2.hashCode());
        assertNotEquals(date2.hashCode(), date1.hashCode());

        //Check equality for different Dates with same parameters
        date2 = new Date(15,6,2019);
        assertEquals(date1.hashCode(), date2.hashCode());
        assertEquals(date2.hashCode(), date1.hashCode());
    }
}