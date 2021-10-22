package com.bft.bookshop.bftbookshop.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", orphanRemoval = true)
    private List<OrderItem> productsAndCount;

    public Order() {
    }

    public List<OrderItem> getProductsAndCount() {
        return productsAndCount;
    }

    public void setProductsAndCount(List<OrderItem> productsAndCount) {
        this.productsAndCount = productsAndCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", productsAndCount=" + productsAndCount +
                '}';
    }
}
