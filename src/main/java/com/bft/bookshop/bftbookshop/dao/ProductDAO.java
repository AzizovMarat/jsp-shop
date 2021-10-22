package com.bft.bookshop.bftbookshop.dao;

import com.bft.bookshop.bftbookshop.entities.CartItem;
import com.bft.bookshop.bftbookshop.entities.Order;
import com.bft.bookshop.bftbookshop.entities.Product;
import com.bft.bookshop.bftbookshop.entities.WarehouseItem;

import java.util.List;
import java.util.Map;

public interface ProductDAO {
    Product[] getAllProducts();
    List<CartItem> getCart();
    void addInCart(int index);
    void removeFromCart(int index);
    List<WarehouseItem> getWarehouse();
    int getCountFromWarehouse(int index);
    Product getProductById(int index);
    void addOrder();
    void deleteOrders();
    List<Order> getOrders();
}
