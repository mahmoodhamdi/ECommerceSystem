/**
 * Utility class for validating payment form inputs.
 * Provides methods for validating credit card numbers, CVV codes, and expiry dates.
 */
public class FormValidator {
    /**
     * Validates a credit card number using the Luhn algorithm.
     * Accepts numbers with or without spaces/hyphens.
     *
     * @param cardNumber The credit card number to validate
     * @return true if the card number is valid, false otherwise
     */
    public static boolean isValidCardNumber(String cardNumber) {
        // Remove any spaces or hyphens
        cardNumber = cardNumber.replaceAll("[-\\s]", "");
        
        // Check if the card number contains only digits and has correct length (13-16 digits)
        if (!cardNumber.matches("\\d{13,16}")) {
            return false;
        }
        
        // Luhn algorithm for credit card validation
        int sum = 0;
        boolean alternate = false;
        
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));
            
            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            
            sum += digit;
            alternate = !alternate;
        }
        
        return (sum % 10 == 0);
    }
    
    /**
     * Validates a CVV (Card Verification Value) number.
     * Must be 3 or 4 digits.
     *
     * @param cvv The CVV to validate
     * @return true if the CVV is valid, false otherwise
     */
    public static boolean isValidCVV(String cvv) {
        return cvv.matches("\\d{3,4}");
    }
    
    /**
     * Validates a credit card expiry date.
     * Must be in MM/YY format and not expired.
     *
     * @param expiryDate The expiry date to validate in MM/YY format
     * @return true if the expiry date is valid and not expired, false otherwise
     */
    public static boolean isValidExpiryDate(String expiryDate) {
        // Check format (MM/YY)
        if (!expiryDate.matches("(0[1-9]|1[0-2])/([0-9]{2})")) {
            return false;
        }
        
        try {
            String[] parts = expiryDate.split("/");
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt(parts[1]) + 2000; // Convert YY to 20YY
            
            java.time.YearMonth expiry = java.time.YearMonth.of(year, month);
            java.time.YearMonth now = java.time.YearMonth.now();
            
            return !expiry.isBefore(now);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Gets an appropriate error message for invalid input.
     *
     * @param fieldName The name of the field being validated ("cardNumber", "cvv", or "expiryDate")
     * @param value The value to validate
     * @return An error message if validation fails, null if validation succeeds
     */
    public static String getErrorMessage(String fieldName, String value) {
        switch (fieldName) {
            case "cardNumber":
                if (!isValidCardNumber(value)) {
                    return "Invalid card number. Please enter a valid 13-16 digit card number.";
                }
                break;
            case "cvv":
                if (!isValidCVV(value)) {
                    return "Invalid CVV. Please enter 3 or 4 digits.";
                }
                break;
            case "expiryDate":
                if (!isValidExpiryDate(value)) {
                    return "Invalid expiry date. Please use MM/YY format and ensure date is not in the past.";
                }
                break;
        }
        return null;
    }
}
