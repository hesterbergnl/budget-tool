package net.hesterberg.budget.utility;

/**
 * Category exception is thrown when a transaction is created that does not have a corresponding category in the budget
 * The software logic can use this exception to prompt the user to create a new category or modify the purchase
 *
 * @author Nikolai Hesterberg
 */
public class CategoryException extends Exception {
    /**
     * Creates a new category exception
     * Calls the parent's constructor with the error message
     *
     * @param errorMessage - custom error message parameter
     */
    public CategoryException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * Null constructor - uses a default errorMessage
     */
    public CategoryException() {
        this("Category does not exist");
    }
}
