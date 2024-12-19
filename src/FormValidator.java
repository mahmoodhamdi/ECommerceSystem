
public class FormValidator {
  
     public static boolean isValidCVV(String cvv) {
        // Check if the CVV contains only digits and has correct length (3-4 digits)
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
        // Check if the expiry date is in the format MM/YY
        if (!expiryDate.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            return false;
        }

        // Check if the expiry date is not in the past
        String[] parts = expiryDate.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt("20" + parts[1]);

        java.util.Calendar now = java.util.Calendar.getInstance();
        int currentMonth = now.get(java.util.Calendar.MONTH) + 1; // Months are 0-based
        int currentYear = now.get(java.util.Calendar.YEAR);

        return (year > currentYear) || (year == currentYear && month >= currentMonth);
    }

    /**
     * Validates an email address.
     * Must be in a valid email format.
     *
     * @param email The email address to validate
     * @return true if the email address is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        // Basic email validation pattern
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailPattern);
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
