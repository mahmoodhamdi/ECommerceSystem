/**
 * Strategy pattern interface for different payment methods.
 * Implementations of this interface handle different types of payments
 * (e.g., credit card, PayPal, etc.).
 *
 * Design Pattern:
 * - Strategy: Defines a family of payment algorithms
 */
public interface PaymentStrategy {
    /**
     * Processes a payment for the given amount.
     *
     * @param amount The amount to be paid
     * @return true if payment was successful, false otherwise
     */
    boolean pay(double amount);
}
