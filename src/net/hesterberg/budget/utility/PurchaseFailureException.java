package net.hesterberg.budget.utility;

/**
 * Custom exception used to alert the UI if there's a failure adding a new purchase
 * Purchase creation has multiple casts and can throw multiple exception types
 * This is a common exception type to make it easier on the UI to handle failures
 *
 * @author Nikolai Hesterberg
 */
public class PurchaseFailureException extends Exception {
    /**
     * Creates a new purchase failure exception
     * Calls the parent's constructor with the error message
     *
     * @param errorMessage - custom error message parameter
     */
    public PurchaseFailureException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * Null constructor - uses a default errorMessage
     */
    public PurchaseFailureException() {
        this("Purchase creation failed");
    }
}
