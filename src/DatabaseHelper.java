import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:ecommerce.db";
    private static Connection connection;

    static {
        try {
            // Establish the database connection
            connection = DriverManager.getConnection(DB_URL);
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTables() throws SQLException {
        String createProductTable = "CREATE TABLE IF NOT EXISTS products (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "price REAL NOT NULL," +
                "description TEXT," +
                "stockQuantity INTEGER NOT NULL" +
                ");";
        Statement stmt = connection.createStatement();
        stmt.execute(createProductTable);
    }

    public static void addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products (name, price, description, stockQuantity) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, product.getName());
        pstmt.setDouble(2, product.getPrice());
        pstmt.setString(3, product.getDescription());
        pstmt.setInt(4, product.getStockQuantity());
        pstmt.executeUpdate();
    }

    public static Product getProduct(int id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            Product product = new Product(
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getInt("stockQuantity")
            );
            product.setId(rs.getInt("id"));
            return product;
        }
        return null;
    }

    public static List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Product product = new Product(
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getInt("stockQuantity")
            );
            product.setId(rs.getInt("id"));
            products.add(product);
        }
        return products;
    }

    public static void updateProduct(Product product) throws SQLException {
        String sql = "UPDATE products SET name = ?, price = ?, description = ?, stockQuantity = ? WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, product.getName());
        pstmt.setDouble(2, product.getPrice());
        pstmt.setString(3, product.getDescription());
        pstmt.setInt(4, product.getStockQuantity());
        pstmt.setInt(5, product.getId());
        pstmt.executeUpdate();
    }

    public static void deleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }

    public static void addInitialProducts() {
        try {
            addProduct(new Product("Laptop", 999.99, "High-performance laptop", 10));
            addProduct(new Product("Smartphone", 499.99, "Latest model smartphone", 20));
            addProduct(new Product("Headphones", 199.99, "Noise-cancelling headphones", 15));
            addProduct(new Product("Smartwatch", 299.99, "Feature-rich smartwatch", 25));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
