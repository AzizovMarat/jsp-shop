package com.bft.bookshop.bftbookshop.entities;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int product_id;
    @Column(name = "product_type", nullable = false)
    private String productType;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "price", nullable = false)
    private int price;
    @Column(name = "img", nullable = false)
    private String img;
    @Column(name = "about", nullable = false, length = 2000)
    private String about;

    public Product() {
    }

    public Product(int product_id, String productType, String productName, String author, int price, String img, String about) {
        this.product_id = product_id;
        this.productType = productType;
        this.productName = productName;
        this.author = author;
        this.price = price;
        this.img = img;
        this.about = about;
    }

    public int getProduct_id() {
        return product_id;
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
                "product_id=" + product_id +
                ", productType='" + productType + '\'' +
                ", productName='" + productName + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", img='" + img + '\'' +
                ", about='" + about + '\'' +
                '}';
    }
}
