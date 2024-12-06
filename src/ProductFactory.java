public class ProductFactory {
    public static Product createProduct(String type, String name, double price, String description, int stockQuantity) {
        switch (type.toLowerCase()) {
            case "electronics":
                return new Electronics(name, price, description, stockQuantity);
            case "clothing":
                return new Clothing(name, price, description, stockQuantity);
            default:
                return new Product(name, price, description, stockQuantity);
        }
    }

    public static Product createDiscountedProduct(Product product, double discountPercentage) {
        return new DiscountedProduct(product, discountPercentage);
    }
}
