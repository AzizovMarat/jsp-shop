package com.bft.bookshop.entities;

import javax.persistence.*;

@Entity
@Table(name = "cart")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "id_products", nullable = false)
    private int idProducts;
    @Column(name = "count", nullable = false)
    private int count;

    public CartItem() {
    }

    public CartItem(int idProducts, int count) {
        this.idProducts = idProducts;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProducts() {
        return idProducts;
    }

    public void setIdProducts(int idProducts) {
        this.idProducts = idProducts;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ItemCart{" +
                "id=" + id +
                ", idProducts=" + idProducts +
                ", count=" + count +
                '}';
    }
}
