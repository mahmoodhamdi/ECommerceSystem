public class DiscountedProduct extends ProductDecorator {
    private double discountPercentage;

    public DiscountedProduct(Product product, double discountPercentage) {
        super(product);
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double getPrice() {
        return super.getPrice() * (1 - discountPercentage / 100.0);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " (" + discountPercentage + "% off)";
    }
}
