package com.bft.bookshop.bftbookshop.entities;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements ProductDAOImpl {

    private final String JDBC_DRIVER = "org.h2.Driver";
    private final String DATABASE_URL = "jdbc:h2:file:c:/Users/m.azizov/IdeaProjects/jsp-shop/db/dbBooks";
    private final String USER = "root";
    private final String PASSWORD = "";

    private Product[] products;

    public ProductDAO() {

        ResultSet allProductsRS;

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            allProductsRS = statement.executeQuery("SELECT * FROM BOOKS");

            List<Product> productList = new ArrayList<>();
            while (allProductsRS.next()) {
                Product product = new Product(allProductsRS.getString("product_type"),
                        allProductsRS.getString("product_name"),
                        allProductsRS.getString("author"),
                        allProductsRS.getInt("price"),
                        allProductsRS.getString("img"),
                        allProductsRS.getString("about"));

                productList.add(product);
            }

            products = new Product[productList.size()];
            for (int i = 0; i < productList.size(); i++) {
                products[i] = productList.get(i);
            }

            allProductsRS.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product[] getAllProducts() {
        return products;
    }
}
