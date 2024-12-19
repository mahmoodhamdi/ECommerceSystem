# E-Commerce System Documentation

## Getting Started

### Setting Up in Visual Studio Code

1. Open the project in Visual Studio Code
2. Run the application:
   - Open the Run and Debug view click play icon in sidebar or press Ctrl+Shift+D
   - Select the Launch ECommerceGUI configuration
   - Click the green play button to start the application

## System Architecture

### Core Components

#### DatabaseHelper

A singleton class managing SQLite database connections and CRUD operations for the products table.

- **Design Pattern**: Singleton
- **Rationale**: Maintains a single database connection instance with global access point

#### ECommerceGUI

The main graphical user interface component displaying products and shopping cart.

- **Design Pattern**: Observer
- **Rationale**: Monitors and responds to shopping cart changes in real-time

#### FormValidator

Handles input validation for various form fields including CVV, expiry date, and email.

#### Product

Data model representing product information including name, price, description, and stock quantity.

#### ProductFactory

Manages product creation and database integration.

- **Design Pattern**: Factory
- **Rationale**: Centralizes product creation logic for improved modularity and extensibility

#### ShoppingCart

Handles shopping cart operations and total price calculations.

- **Design Pattern**: Singleton
- **Rationale**: Maintains consistent cart state across the application

#### PaymentStrategy

Defines the payment processing interface.

- **Design Pattern**: Strategy
- **Rationale**: Enables flexible payment method implementation and switching

#### CreditCardPayment

Credit card payment implementation of the PaymentStrategy interface.

## Validation Rules

### CVV Validation

- 3 or 4 digits required
- Numbers only

### Expiry Date Validation

- Format: MM/YY
- Month range: 01-12
- Must be future date
- Assumes 20YY for year

### Email Validation

- Standard email format checking

## Application Setup

### Initial Product Setup

The system can be populated with initial products using the DatabaseHelper.addInitialProducts() method:

```java
public static void main(String[] args) {
    // Add initial products to the database
    DatabaseHelper.addInitialProducts();
    SwingUtilities.invokeLater(() -> new ECommerceGUI());
}
```

## Dependencies

- SQLite JDBC Driver
- Java Swing

## License

This project is released under the [MIT License](https://opensource.org/licenses/MIT).

## Acknowledgments

- Professor Dr. Mahmoud Al-Bassiouni, for the course and guidance.
- [SQLite JDBC Driver](https://sqlite.org/jdbc.html)
- [Java Swing](https://docs.oracle.com/javase/tutorial/uiswing/learn/index.html)
- [MIT License](https://opensource.org/licenses/MIT)
- [Observer Pattern](https://en.wikipedia.org/wiki/Observer_pattern)
- [Singleton Pattern](https://en.wikipedia.org/wiki/Singleton_pattern)
- [Factory Pattern](https://en.wikipedia.org/wiki/Factory_method_pattern)
- [Strategy Pattern](https://en.wikipedia.org/wiki/Strategy_pattern)
- [Design Patterns](https://refactoring.guru/design-patterns)
- [Java Documentation](https://docs.oracle.com/javase/8/docs/api/)
