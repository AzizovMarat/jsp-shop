package com.bft.bookshop.bftbookshop.entities;

import javax.persistence.*;

@Entity
@Table(name = "warehouse")
public class WarehouseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "id_products")
    private int idProducts;
    @Column(name = "count")
    private int count;

    public WarehouseItem() {
    }

    public WarehouseItem(int idProducts, int count) {
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
        return "ItemWarehouse{" +
                "id=" + id +
                ", idProducts=" + idProducts +
                ", count=" + count +
                '}';
    }
}
