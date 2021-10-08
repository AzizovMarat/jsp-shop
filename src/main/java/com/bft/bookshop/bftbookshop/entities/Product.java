package com.bft.bookshop.bftbookshop.entities;

public class Product {
    private String productType;
    private String productName;
    private String author;
    private int price;
    private String img;
    private String about;

    public Product(String productType, String productName, String author, int price, String img, String about) {
        this.productType = productType;
        this.productName = productName;
        this.author = author;
        this.price = price;
        this.img = img;
        this.about = about;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productType='" + productType + '\'' +
                ", productName='" + productName + '\'' +
                ", author='" + author + '\'' +
                ", price='" + price + '\'' +
                ", img='" + img + '\'' +
                ", about='" + about + '\'' +
                '}';
    }
}
