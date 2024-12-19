import java.sql.SQLException;

public class ProductFactory {
    public static Product createProduct(String type, String name, double price, String description, int stockQuantity) {
        Product product;
        switch (type.toLowerCase()) {
            case "electronics":
                product = new Electronics(name, price, description, stockQuantity);
                break;
            case "clothing":
                product = new Clothing(name, price, description, stockQuantity);
                break;
            default:
                product = new Product(name, price, description, stockQuantity);
        }
        try {
            DatabaseHelper.addProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public static Product createDiscountedProduct(Product product, double discountPercentage) {
        Product discountedProduct = new DiscountedProduct(product, discountPercentage);
        try {
            DatabaseHelper.addProduct(discountedProduct);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discountedProduct;
    }
}
