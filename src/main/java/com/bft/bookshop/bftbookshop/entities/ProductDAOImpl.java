package com.bft.bookshop.bftbookshop.entities;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sun.swing.MenuItemLayoutHelper.max;

public class ProductDAOImpl implements ProductDAO {

    private final String JDBC_DRIVER = "org.h2.Driver";
    private final String DATABASE_URL = "jdbc:h2:file:c:/Users/m.azizov/IdeaProjects/jsp-shop/db/dbBooks";
    private final String USER = "root";
    private final String PASSWORD = "";

    private final Product[] products;

    public ProductDAOImpl() {
        products = getProducts();
    }

    @Override
    public void deleteOrders() {
        executeUpdate("DROP TABLE IF EXISTS ORDERS;");
        executeUpdate("CREATE TABLE ORDERS (id INT NOT NULL, id_products INT NOT NULL, count INT NOT NULL);");

        executeUpdate("DROP TABLE IF EXISTS WAREHOUSE;");
        executeUpdate("CREATE TABLE WAREHOUSE (id INT AUTO_INCREMENT PRIMARY KEY, id_products INT NOT NULL, count INT NOT NULL);");
        executeUpdate("INSERT INTO WAREHOUSE (id_products, count) VALUES (1, 0), (2, 2), (3, 3), (4, 0), (5, 5), (6, 6);");
    }

    @Override
    public void addOrder() {

        List<int[]> cart = getCart();
        Map<Integer, List<int[]>> orders = getOrders();

        int lastId = 0;
        for (Integer key : orders.keySet()) {
            lastId = max(key, lastId);
        }
        lastId++;

        for (int[] ints : cart) {
            String query = String.format("INSERT INTO ORDERS (id, id_products, count) VALUES ('%d', '%d', '%d');",
                    lastId,
                    ints[0],
                    ints[1]);
            executeUpdate(query);

            int countFromWarehouse = getCountFromWarehouse(ints[0]);
            query = String.format("UPDATE WAREHOUSE SET count = '%d' WHERE id_products = '%d';",
                    countFromWarehouse - ints[1],
                    ints[0]);
            executeUpdate(query);
        }


        executeUpdate("DROP TABLE IF EXISTS CART;");
        executeUpdate("CREATE TABLE CART (id INT AUTO_INCREMENT PRIMARY KEY, id_products INT NOT NULL, count INT NOT NULL);");
    }

    @Override
    public Map<Integer, List<int[]>> getOrders() {

        ResultSet ordersRS;
        Map<Integer, List<int[]>> orders = new HashMap<>();

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            ordersRS = statement.executeQuery("SELECT * FROM ORDERS");

            if (ordersRS != null) {
                try {
                    while (ordersRS.next()) {
                        int id = ordersRS.getInt("id");
                        int id_products = ordersRS.getInt("id_products");
                        int count = ordersRS.getInt("count");
                        if (!orders.containsKey(id)) {
                            orders.put(id, new ArrayList<>());
                        }
                        orders.get(id).add(new int[]{id_products, count});
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        ordersRS.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("Orders is empty.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public Product getProductById(int index) {

        ResultSet productRS;
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            String query = String.format("SELECT * FROM BOOKS WHERE id = '%d';", index);
            productRS = statement.executeQuery(query);

            if (productRS != null) {
                try {
                    productRS.next();
                    return new Product(productRS.getInt("id"),
                            productRS.getString("product_type"),
                            productRS.getString("product_name"),
                            productRS.getString("author"),
                            productRS.getInt("price"),
                            productRS.getString("img"),
                            productRS.getString("about"));

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        productRS.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("Warehouse is empty.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int getCountFromWarehouse(int index) {
        List<int[]> warehouse = getWarehouse();
        if (!warehouse.isEmpty()) {
            for (int[] ints : warehouse) {
                if (ints[0] == index) {
                    return ints[1];
                }
            }
        }
        return 0;
    }

    @Override
    public List<int[]> getWarehouse() {

        ResultSet warehouseRS;
        List<int[]> warehouse = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            warehouseRS = statement.executeQuery("SELECT * FROM WAREHOUSE");

            if (warehouseRS != null) {
                try {
                    while (warehouseRS.next()) {
                        warehouse.add(new int[]{warehouseRS.getInt("id_products"), warehouseRS.getInt("count")});
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        warehouseRS.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("Warehouse is empty.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return warehouse;
    }

    @Override
    public List<int[]> getCart() {

        ResultSet allCartProductsRS;
        List<int[]> cart = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            allCartProductsRS = statement.executeQuery("SELECT * FROM CART");

            if (allCartProductsRS != null) {
                try {
                    while (allCartProductsRS.next()) {
                        cart.add(new int[]{allCartProductsRS.getInt("id_products"), allCartProductsRS.getInt("count")});
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        allCartProductsRS.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("Cart is empty.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cart;
    }

    @Override
    public void addInCart(int index) {
        List<int[]> cart = getCart();
        if (!cart.isEmpty()) {
            for (int[] ints : cart) {
                if (ints[0] == index) {
                    int countFromWarehouse = getCountFromWarehouse(index);
                    String query = String.format("UPDATE CART SET count = '%d' WHERE id_products = '%d';",
                            ints[1] + 1 > countFromWarehouse ? ints[1] : (ints[1] += 1),
                            index);
                    executeUpdate(query);
                    return;
                }
            }
        }
        String query = String.format("INSERT INTO CART (id_products, count) VALUES ('%d', '%d');", index, 1);
        executeUpdate(query);
    }

    @Override
    public void removeFromCart(int index) {
        String query = String.format("DELETE FROM CART WHERE id_products = '%d';", index);
        executeUpdate(query);
    }

    @Override
    public Product[] getAllProducts() {
        return products;
    }

    private Product[] getProducts() {
        Product[] products = null;
        ResultSet resultSet;

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            resultSet = statement.executeQuery("SELECT * FROM BOOKS");

            List<Product> productList = new ArrayList<>();
            while (resultSet.next()) {
                Product product = new Product(resultSet.getInt("id"),
                        resultSet.getString("product_type"),
                        resultSet.getString("product_name"),
                        resultSet.getString("author"),
                        resultSet.getInt("price"),
                        resultSet.getString("img"),
                        resultSet.getString("about"));

                productList.add(product);
            }

            products = new Product[productList.size()];
            for (int i = 0; i < productList.size(); i++) {
                products[i] = productList.get(i);
            }
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    private void executeUpdate(String query) {

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            statement.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
