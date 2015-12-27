package es.hol.ecotiffins.model;

import java.io.Serializable;

public class Order implements Serializable{
    private String title;
    private String subtitle;
    private int quantity;
    private String price;
    private int imgIcon;
    private String date;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", quantity=" + quantity +
                ", price='" + price + '\'' +
                ", imgIcon=" + imgIcon +
                '}';
    }
}
