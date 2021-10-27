package com.bft.bookshop.entities;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_products", nullable = false)
    private int idProducts;

    @Column(name = "count", nullable = false)
    private int count;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public OrderItem() {
    }

    public OrderItem(Order order) {
        this.order = order;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getIdProducts() {
        return idProducts;
    }

    public void setIdProducts(int idProducts) {
        this.idProducts = idProducts;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", idProducts=" + idProducts +
                ", count=" + count +
                '}';
    }
}
