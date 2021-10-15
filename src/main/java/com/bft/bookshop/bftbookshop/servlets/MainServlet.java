package com.bft.bookshop.bftbookshop.servlets;

import com.bft.bookshop.bftbookshop.entities.ProductDAOImpl;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet("/main")
public class MainServlet extends HttpServlet {
    private ProductDAOImpl productDAOImpl;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDAOImpl = new ProductDAOImpl();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String methodName = req.getParameter("method");
        if (methodName == null) methodName = "showMainContent";

        try {
            Method method = this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showMainContent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products", productDAOImpl.getAllProducts());
        req.setAttribute("warehouse", productDAOImpl.getWarehouse());

        HttpSession session = req.getSession();
        String strOnlyInWarehouse = req.getParameter("onlyInWarehouse");
        boolean onlyInWarehouse = false;
        if (strOnlyInWarehouse != null) {
            onlyInWarehouse = Boolean.parseBoolean(strOnlyInWarehouse);
        } else {
            Object sessionAttrOnlyInWarehouse = session.getAttribute("onlyInWarehouse");
            if (sessionAttrOnlyInWarehouse != null) {
                onlyInWarehouse = (boolean) sessionAttrOnlyInWarehouse;
            }
        }
        session.setAttribute("onlyInWarehouse", onlyInWarehouse);

        req.setAttribute("page", "bookshop");
        req.getRequestDispatcher("/jsp/bookshop.jsp").forward(req, resp);
    }

    public void removeFromCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        productDAOImpl.removeFromCart(id);
        showCart(req, resp);
    }

    public void addInCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        productDAOImpl.addInCart(id);
        showProduct(req, resp);
    }

    public void showCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products", productDAOImpl.getAllProducts());
        req.setAttribute("cart", productDAOImpl.getCart());
        req.setAttribute("page", "cart");
        req.getRequestDispatcher("/jsp/bookshop.jsp").forward(req, resp);
    }

    public void showProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("product", productDAOImpl.getProductById(Integer.parseInt(req.getParameter("id"))));
        req.setAttribute("warehouseCount", productDAOImpl.getCountFromWarehouse(Integer.parseInt(req.getParameter("id"))));
        req.setAttribute("id", req.getParameter("id"));
        req.setAttribute("page", "product");
        req.getRequestDispatcher("/jsp/bookshop.jsp").forward(req, resp);
    }

    public void addOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        productDAOImpl.addOrder();
        req.setAttribute("products", productDAOImpl.getAllProducts());
        req.setAttribute("orders", productDAOImpl.getOrders());
        req.setAttribute("page", "orders");
        req.getRequestDispatcher("/jsp/bookshop.jsp").forward(req, resp);
    }

    public void deleteOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        productDAOImpl.deleteOrders();
        showMainContent(req, resp);
    }
}