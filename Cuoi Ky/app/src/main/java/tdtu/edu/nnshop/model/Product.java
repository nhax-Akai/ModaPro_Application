package tdtu.edu.nnshop.model;
import java.io.Serializable;

public class Product implements Serializable{

    private String id;
    private String name;
    private String img_url;
    private double price;
    private String description;
    private String category;
    private String color;
    private int quantity;

    public Product() {
    }

    public Product(String name, String img_url, double price, String description, String category, String color, int quantity) {
        this.name = name;
        this.img_url = img_url;
        this.price = price;
        this.description = description;
        this.category = category;
        this.color = color;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
