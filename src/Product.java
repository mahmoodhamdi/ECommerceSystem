public class Product {
    private String name;
    private double price;
    private String description;
    private int stockQuantity;

    public Product(String name, double price, String description, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stockQuantity = stockQuantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int quantity) {
        this.stockQuantity = quantity;
    }

    @Override
    public String toString() {
        return name + " - $" + String.format("%.2f", price);
    }
}
