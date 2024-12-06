public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cvv;
    private String expiryDate;

    public CreditCardPayment(String cardNumber, String cvv, String expiryDate) throws IllegalArgumentException {
        validateFields(cardNumber, cvv, expiryDate);
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    private void validateFields(String cardNumber, String cvv, String expiryDate) throws IllegalArgumentException {
        StringBuilder errors = new StringBuilder();
        
        String cardError = FormValidator.getErrorMessage("cardNumber", cardNumber);
        String cvvError = FormValidator.getErrorMessage("cvv", cvv);
        String expiryError = FormValidator.getErrorMessage("expiryDate", expiryDate);
        
        if (cardError != null) errors.append(cardError).append("\n");
        if (cvvError != null) errors.append(cvvError).append("\n");
        if (expiryError != null) errors.append(expiryError).append("\n");
        
        if (errors.length() > 0) {
            throw new IllegalArgumentException(errors.toString());
        }
    }

    @Override
    public boolean pay(double amount) {
        // In a real implementation, this would integrate with a payment gateway
        System.out.println("Processing payment of $" + String.format("%.2f", amount));
        System.out.println("Using credit card: " + maskCardNumber());
        return true;
    }

    private String maskCardNumber() {
        String cleaned = cardNumber.replaceAll("[-\\s]", "");
        return "****-****-****-" + cleaned.substring(cleaned.length() - 4);
    }
}
