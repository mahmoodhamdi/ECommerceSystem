import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ECommerceGUI implements Observer {
    private JFrame frame;
    private JPanel productPanel;
    private JPanel cartPanel;
    private JLabel totalLabel;
    private JTextArea messageArea;
    private ShoppingCart cart;
    private List<Product> availableProducts;
    private PaymentStrategy paymentStrategy;

    public ECommerceGUI() {
        cart = ShoppingCart.getInstance();
        cart.registerObserver(this);
        initializeProducts();
        setupGUI();
    }

    private void initializeProducts() {
        availableProducts = new ArrayList<>();
        try {
            // Fetch products from the database
            availableProducts = DatabaseHelper.getAllProducts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupGUI() {
        frame = new JFrame("E-Commerce System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(1000, 600);

        // Products Panel
        productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        productPanel.setBorder(BorderFactory.createTitledBorder("Available Products"));
        
        for (Product product : availableProducts) {
            JPanel productItemPanel = createProductPanel(product);
            productPanel.add(productItemPanel);
        }

        // Cart Panel
        cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
        cartPanel.setBorder(BorderFactory.createTitledBorder("Shopping Cart"));
        
        // Message Area for Observer pattern updates
        messageArea = new JTextArea(5, 40);
        messageArea.setEditable(false);
        JScrollPane messageScroll = new JScrollPane(messageArea);
        messageScroll.setBorder(BorderFactory.createTitledBorder("System Messages"));
        
        totalLabel = new JLabel("Total: $0.00");
        
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(e -> handleCheckout());

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(totalLabel, BorderLayout.WEST);
        southPanel.add(checkoutButton, BorderLayout.EAST);

        // Layout
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(productPanel),
                new JScrollPane(cartPanel));
        splitPane.setDividerLocation(400);

        frame.add(splitPane, BorderLayout.CENTER);
        frame.add(messageScroll, BorderLayout.SOUTH);
        frame.add(southPanel, BorderLayout.NORTH);

        frame.setVisible(true);
    }

    private JPanel createProductPanel(Product product) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createEtchedBorder());

        JLabel nameLabel = new JLabel(product.getName());
        JLabel priceLabel = new JLabel(String.format("$%.2f", product.getPrice()));
        JLabel descLabel = new JLabel(product.getDescription());
        JButton addButton = new JButton("Add to Cart");

        addButton.addActionListener(e -> {
            cart.addProduct(product);
            updateCartDisplay();
        });

        panel.add(nameLabel);
        panel.add(priceLabel);
        panel.add(descLabel);
        panel.add(addButton);

        return panel;
    }

    private void updateCartDisplay() {
        cartPanel.removeAll();
        
        for (Product product : cart.getProducts()) {
            JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            itemPanel.add(new JLabel(product.toString()));
            
            JButton removeButton = new JButton("Remove");
            removeButton.addActionListener(e -> {
                cart.removeProduct(product);
                updateCartDisplay();
            });
            
            itemPanel.add(removeButton);
            cartPanel.add(itemPanel);
        }

        totalLabel.setText(String.format("Total: $%.2f", cart.getTotal()));
        cartPanel.revalidate();
        cartPanel.repaint();
    }

    private void handleCheckout() {
        if (cart.getProducts().isEmpty()) {
            JOptionPane.showMessageDialog(frame, 
                "Cart is empty!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Using Strategy pattern for payment
        String[] options = {"Credit Card", "Cancel"};
        int choice = JOptionPane.showOptionDialog(frame,
                "Select payment method:",
                "Payment",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) {
            showPaymentDialog();
        }
    }

    private void showPaymentDialog() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JTextField cardNumberField = new JTextField(16);
        JTextField cvvField = new JTextField(4);
        JTextField expiryField = new JTextField(5);
        
        // Add input hints
        cardNumberField.setToolTipText("Enter 13-16 digit card number");
        cvvField.setToolTipText("Enter 3-4 digit CVV");
        expiryField.setToolTipText("Enter expiry date (MM/YY)");
        
        panel.add(new JLabel("Card Number:"));
        panel.add(cardNumberField);
        panel.add(new JLabel("CVV:"));
        panel.add(cvvField);
        panel.add(new JLabel("Expiry Date (MM/YY):"));
        panel.add(expiryField);

        while (true) {
            int result = JOptionPane.showConfirmDialog(frame, panel, 
                    "Enter Payment Details", 
                    JOptionPane.OK_CANCEL_OPTION);
                    
            if (result == JOptionPane.OK_OPTION) {
                try {
                    paymentStrategy = new CreditCardPayment(
                        cardNumberField.getText(),
                        cvvField.getText(),
                        expiryField.getText()
                    );
                    
                    if (paymentStrategy.pay(cart.getTotal())) {
                        JOptionPane.showMessageDialog(frame, 
                            "Payment successful!", 
                            "Success", 
                            JOptionPane.INFORMATION_MESSAGE);
                        cart.clear();
                        updateCartDisplay();
                        break;
                    }
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(frame,
                        e.getMessage(),
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                    continue;
                }
            } else {
                break;
            }
        }
    }

    @Override
    public void update(String message) {
        messageArea.append(message + "\n");
        messageArea.setCaretPosition(messageArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        // Add initial products to the database
        DatabaseHelper.addInitialProducts();

        SwingUtilities.invokeLater(() -> new ECommerceGUI());
    }
}
