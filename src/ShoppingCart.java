import java.util.ArrayList;
import java.util.List;

/**
 * Shopping Cart implementation using the Singleton pattern.
 * This class manages the user's shopping cart and implements the Subject interface
 * to notify observers of any changes to the cart's state.
 *
 * Design Patterns:
 * - Singleton: Ensures only one cart instance exists
 * - Observer: Notifies GUI of cart changes
 */
public class ShoppingCart implements Subject {
    private static ShoppingCart instance;
    private List<Product> products;
    private double total;
    private List<Observer> observers;

    /**
     * Private constructor to enforce Singleton pattern.
     * Initializes empty lists for products and observers.
     */
    private ShoppingCart() {
        products = new ArrayList<>();
        observers = new ArrayList<>();
        total = 0.0;
    }

    /**
     * Gets the singleton instance of the shopping cart.
     * @return The single instance of ShoppingCart
     */
    public static ShoppingCart getInstance() {
        if(instance == null) {
            instance = new ShoppingCart();
        }
        return instance;
    }

    /**
     * Registers an observer to receive cart updates.
     * @param observer The observer to register
     */
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the notification list.
     * @param observer The observer to remove
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers with a message.
     * @param message The message to send to observers
     */
    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    /**
     * Adds a product to the cart and notifies observers.
     * @param product The product to add
     */
    public void addProduct(Product product) {
        products.add(product);
        calculateTotal();
        notifyObservers("Product added: " + product.getName());
    }

    /**
     * Removes a product from the cart and notifies observers.
     * @param product The product to remove
     */
    public void removeProduct(Product product) {
        products.remove(product);
        calculateTotal();
        notifyObservers("Product removed: " + product.getName());
    }

    /**
     * Calculates the total price of all products in the cart.
     */
    private void calculateTotal() {
        total = products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    /**
     * Gets a copy of the product list.
     * @return A new ArrayList containing all products
     */
    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    /**
     * Gets the current total price of the cart.
     * @return The total price
     */
    public double getTotal() {
        return total;
    }

    /**
     * Clears all products from the cart and notifies observers.
     */
    public void clear() {
        products.clear();
        total = 0.0;
        notifyObservers("Cart cleared");
    }
}
