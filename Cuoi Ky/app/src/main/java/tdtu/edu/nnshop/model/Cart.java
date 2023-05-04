package tdtu.edu.nnshop.model;

public class Cart {

    private String id;
    private String product_name;
    private String product_img_url;
    private double product_price;
    private String product_color;
    private int product_quantity;
    private String current_date;
    private String current_time;
    private double total_price;
    //constructor
    public Cart() {
    }

    public Cart(String product_name, String product_img_url, double product_price, String product_color, int product_quantity, String current_date, String current_time, double total_price) {
        this.product_name = product_name;
        this.product_img_url = product_img_url;
        this.product_price = product_price;
        this.product_color = product_color;
        this.product_quantity = product_quantity;
        this.current_date = current_date;
        this.current_time = current_time;
        this.total_price = total_price;
    }
    //setter and getter


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_img_url() {
        return product_img_url;
    }

    public void setProduct_img_url(String product_img_url) {
        this.product_img_url = product_img_url;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public String getProduct_color() {
        return product_color;
    }

    public void setProduct_color(String product_color) {
        this.product_color = product_color;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }

    public String getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(String current_time) {
        this.current_time = current_time;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }
}
