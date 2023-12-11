package entity;
import java.util.Objects;

public class Product {
    private String type;
    private String name;
    private double price;
    private int stock;

    public Product(String type, String name, double price, int stock) {
        this.type = type;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }


    public int getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


    @Override
    public int hashCode() {
        return Objects.hash(type, name, price, stock);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return Double.compare(product.price, price) == 0 &&
                stock == product.stock &&
                Objects.equals(type, product.type) &&
                Objects.equals(name, product.name);
    }

    @Override
    public String toString() {
        return String.format("%s; %s; $%.2f; Stock: %d", type, name, price, stock);
    }
}
