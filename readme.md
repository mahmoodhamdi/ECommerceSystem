# E-Commerce System

A Java-based E-Commerce system implementing various design patterns and featuring a modern GUI interface.

## Design Patterns Implemented

### 1. Singleton Pattern

- **Class**: `ShoppingCart`
- **Purpose**: Ensures only one shopping cart instance exists throughout the application
- **Implementation**: Private constructor and static getInstance() method

### 2. Factory Pattern

- **Class**: `ProductFactory`
- **Purpose**: Creates different types of products without exposing creation logic
- **Products**:
  - Electronics
  - Clothing
  - Discounted Products

### 3. Observer Pattern

- **Interface**: `Observer`, `Subject`
- **Purpose**: Updates GUI components when cart changes
- **Implementation**:
  - ShoppingCart (Subject) notifies observers of changes
  - GUI (Observer) updates display accordingly

### 4. Decorator Pattern

- **Class**: `ProductDecorator`
- **Purpose**: Adds additional behaviors to products dynamically
- **Implementation**:
  - `DiscountedProduct` extends ProductDecorator
  - Allows adding discounts to any product type

### 5. Strategy Pattern

- **Interface**: `PaymentStrategy`
- **Purpose**: Encapsulates different payment methods
- **Implementation**:
  - `CreditCardPayment` strategy with validation
  - Easily extensible for new payment methods

## Features

### Product Management

- Different product categories (Electronics, Clothing)
- Product details (name, price, description, stock)
- Discount support
- Dynamic product creation using Factory pattern

### Shopping Cart

- Add/remove products
- Real-time total calculation
- Cart state persistence using Singleton pattern
- Observable cart changes

### Payment Processing

- Credit card payment support
- Comprehensive form validation:
  - Luhn algorithm for card number validation
  - CVV validation (3-4 digits)
  - Expiry date validation (MM/YY format)
  - Future date verification

### GUI Features

- Split-pane interface
- Product listing with details
- Interactive shopping cart
- System message area
- Form validation feedback
- Payment processing dialog

## Form Validation Details

### Credit Card Validation

- Card number must be 13-16 digits
- Must pass Luhn algorithm check
- Spaces and hyphens are automatically removed
- Masked display for security

### CVV Validation

- Must be 3 or 4 digits
- Numeric only

### Expiry Date Validation

- MM/YY format required
- Month must be 01-12
- Must be a future date
- Automatic century handling (20YY)

## Class Structure

### Core Classes

```base
Product (base class)
├── Electronics
├── Clothing
└── ProductDecorator
    └── DiscountedProduct

ShoppingCart (Singleton)
└── implements Subject

PaymentStrategy (interface)
└── CreditCardPayment

Observer (interface)
└── ECommerceGUI
```

### Utility Classes

```base
FormValidator
└── Credit Card Validation Methods
```

## Usage Examples

### Creating Products

```java
// Create regular product
Product laptop = ProductFactory.createProduct("electronics", "Laptop", 999.99, "High-performance laptop", 10);

// Create discounted product
Product discountedPhone = ProductFactory.createDiscountedProduct(
    ProductFactory.createProduct("electronics", "Phone", 599.99, "Smartphone", 15),
    20 // 20% discount
);
```

### Payment Processing

```java
// Example of valid payment details
String cardNumber = "4532015112830366";
String cvv = "123";
String expiryDate = "12/25";

try {
    PaymentStrategy payment = new CreditCardPayment(cardNumber, cvv, expiryDate);
    payment.pay(totalAmount);
} catch (IllegalArgumentException e) {
    // Handle validation errors
}
```

## Error Handling

The system provides comprehensive error handling:

- Input validation with detailed error messages
- Payment processing validation
- Stock availability checking
- GUI state management

## Future Enhancements

- Additional payment methods
- User authentication
- Order history
- Inventory management
- More product categories
- Advanced search functionality
