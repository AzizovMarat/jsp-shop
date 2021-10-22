package com.bft.bookshop.bftbookshop.dao;

import com.bft.bookshop.bftbookshop.HibernateUtil;
import com.bft.bookshop.bftbookshop.entities.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    private final Product[] products;

    public ProductDAOImpl() {
        products = getProducts();
    }

    @Override
    public void deleteOrders() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

//        session.createSQLQuery("DROP TABLE IF EXISTS ORDER_ITEMS;").executeUpdate();
//        session.createSQLQuery("CREATE TABLE ORDER_ITEMS (id INT AUTO_INCREMENT PRIMARY KEY, id_products INT NOT NULL, count INT NOT NULL);").executeUpdate();
        session.createQuery("delete from OrderItem").executeUpdate();

//        session.createSQLQuery("DROP TABLE IF EXISTS ORDERS;").executeUpdate();
//        session.createSQLQuery("CREATE TABLE ORDERS (id INT AUTO_INCREMENT PRIMARY KEY, id_products INT NOT NULL, count INT NOT NULL);").executeUpdate();
        session.createQuery("delete from Order").executeUpdate();

        session.createSQLQuery("DROP TABLE IF EXISTS WAREHOUSE").executeUpdate();
        session.createSQLQuery("CREATE TABLE WAREHOUSE (id INT AUTO_INCREMENT PRIMARY KEY, id_products INT NOT NULL, count INT NOT NULL);").executeUpdate();
        session.createSQLQuery("INSERT INTO WAREHOUSE (id_products, count) VALUES (1, 0), (2, 2), (3, 3), (4, 0), (5, 5), (6, 6);").executeUpdate();
//        session.createQuery("delete from WarehouseItem").executeUpdate();
//        for (int i = 1; i < 7; i++) {
//            session.saveOrUpdate(new WarehouseItem(1, i));
//        }

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void addOrder() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        List<CartItem> cart = getCart();
        List<WarehouseItem> warehouse = getWarehouse();

        Order order = new Order();
        order.setProductsAndCount(new ArrayList<>());
        session.save(order);

        for (CartItem ci : cart) {
            OrderItem oi = new OrderItem(order);
            oi.setIdProducts(ci.getIdProducts());
            oi.setCount(ci.getCount());
            order.getProductsAndCount().add(oi);
            session.save(oi);

            for (WarehouseItem wi : warehouse) {
                if (wi.getId() == ci.getIdProducts()) {
                    wi.setCount(wi.getCount() - ci.getCount());
                    session.update(wi);
                }
            }
        }

        session.createSQLQuery("DROP TABLE IF EXISTS CART;").executeUpdate();
        session.createSQLQuery("CREATE TABLE CART (id INT AUTO_INCREMENT PRIMARY KEY, id_products INT NOT NULL, count INT NOT NULL);").executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    private Integer getMaxIdFromOrders() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Integer lastId = (Integer) session.createQuery("select max(id) from Order").getSingleResult();
        if (lastId == null) lastId = 0;

        session.getTransaction().commit();
        session.close();

        return lastId;
    }

    @Override
    public List<Order> getOrders() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        List<Order> orders = session.createQuery("from Order").list();
        System.out.println("List orders: " + orders);

        session.getTransaction().commit();
        session.close();

        return orders;
    }

    @Override
    public Product getProductById(int index) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Product product = session.get(Product.class, index);

        session.getTransaction().commit();
        session.close();

        return product;
    }

    @Override
    public int getCountFromWarehouse(int index) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        WarehouseItem warehouse = session.get(WarehouseItem.class, index);

        session.getTransaction().commit();
        session.close();

        return warehouse.getCount();
    }

    @Override
    public List<WarehouseItem> getWarehouse() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        List<WarehouseItem> warehouse = session.createQuery("from WarehouseItem").list();
        System.out.println("Warehouse: " + warehouse);
        session.getTransaction().commit();
        session.close();

        return warehouse;
    }

    @Override
    public List<CartItem> getCart() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        List<CartItem> cart = session.createQuery("from CartItem").list();

        session.getTransaction().commit();
        session.close();

        return cart;
    }

    @Override
    public void addInCart(int index) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Query<CartItem> query = session.createQuery("from CartItem where idProducts = :id");
            query.setParameter("id", index);
            List<CartItem> cart = query.list();
            int countFromWarehouse = getCountFromWarehouse(index);
            if (!cart.isEmpty()) {
                for (CartItem ci : cart) {
                    if (ci.getCount() + 1 <= countFromWarehouse) {
                        ci.setCount(ci.getCount() + 1);
                    }
                }
            } else {
                session.save(new CartItem(index, 1));
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public void removeFromCart(int index) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Query query = session.createQuery("delete from CartItem where id = :id");
            query.setParameter("id", index);
            query.executeUpdate();

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public Product[] getAllProducts() {
        return products;
    }

    private Product[] getProducts() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        List<Product> productList = session.createQuery("from Product").list();
        Product[] products = new Product[productList.size()];
        productList.toArray(products);

        session.getTransaction().commit();
        session.close();

        return products;
    }

}
