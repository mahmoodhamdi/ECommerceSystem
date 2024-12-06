public class Electronics extends Product {
    public Electronics(String name, double price, String description, int stockQuantity) {
        super(name, price, description, stockQuantity);
    }
    public void displayDetails() {
        System.out.println("This is an electronic product.");
    }
}
