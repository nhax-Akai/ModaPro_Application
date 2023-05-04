package tdtu.edu.nnshop.model;

public class BestSeller {
    private String name;
    private String description;
    private double price;
    private String color;
    private String img_url;
    private int quantity;
    private String category;

    public BestSeller() {
    }

    public BestSeller(String name, String description, double price, String color, String img_url, int quantity, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.color = color;
        this.img_url = img_url;
        this.quantity = quantity;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
