package es.hol.ecotiffins.model;

import java.io.Serializable;

public class History implements Serializable{
    private String title;
    private String subtitle;
    private String price;
    private int imgIcon;
    private String date;

    public History(String title, String subtitle, String price, String date) {
        this.title = title;
        this.subtitle = subtitle;
        this.price = price;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImgIcon() {
        return imgIcon;
    }

    public void setImgIcon(int imgIcon) {
        this.imgIcon = imgIcon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "History{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", price='" + price + '\'' +
                ", imgIcon=" + imgIcon +
                ", date='" + date + '\'' +
                '}';
    }
}