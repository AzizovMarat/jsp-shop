package com.bft.bookshop.dao;

import com.bft.bookshop.entities.CartItem;
import com.bft.bookshop.entities.Order;
import com.bft.bookshop.entities.Product;
import com.bft.bookshop.entities.WarehouseItem;

import java.util.List;

public interface ProductDAO {
    Product[] getAllProducts();
    List<CartItem> getCart();
    void addToCart(int index);
    void removeFromCart(int index);
    List<WarehouseItem> getWarehouse();
    int getCountFromWarehouse(int index);
    Product getProductById(int index);
    void addOrder();
    void deleteOrders();
    List<Order> getOrders();
}
