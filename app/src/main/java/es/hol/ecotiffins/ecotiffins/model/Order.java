package es.hol.ecotiffins.ecotiffins.model;

import android.widget.ImageView;

public class Order {
    private String title;
    private String subtitle;
    private int quantity;
    private String price;
    private int imgIcon;

    public Order(String title, String subtitle, int quantity, String price, int imgIcon) {
        this.title = title;
        this.subtitle = subtitle;
        this.quantity = quantity;
        this.price = price;
        this.imgIcon = imgIcon;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public int getImgIcon() {
        return imgIcon;
    }
}
