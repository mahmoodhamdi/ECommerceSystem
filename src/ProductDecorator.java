public abstract class ProductDecorator extends Product {
    protected Product decoratedProduct;

    public ProductDecorator(Product product) {
        super(product.getName(), product.getPrice(), product.getDescription(), product.getStockQuantity());
        this.decoratedProduct = product;
    }

    @Override
    public double getPrice() {
        return decoratedProduct.getPrice();
    }

    @Override
    public String getDescription() {
        return decoratedProduct.getDescription();
    }
}
