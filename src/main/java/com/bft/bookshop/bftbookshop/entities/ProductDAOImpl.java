package com.bft.bookshop.bftbookshop.entities;

import java.util.List;
import java.util.Map;

public interface ProductDAOImpl {
    Product[] getAllProducts();
    List<int[]> getCart();
    void addInCart(int index);
    void removeFromCart(int index);
    List<int[]> getWarehouse();
    int getCountFromWarehouse(int index);
    Product getProductById(int index);
    void addOrder();
    void deleteOrders();
    Map<Integer, List<int[]>> getOrders();
}
