package com.bft.bookshop.controllers;

import com.bft.bookshop.dao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;

@Controller
@RequestMapping("/main")
public class MainController {

    private final ProductDAO productDAO;

    @Autowired
    public MainController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GetMapping
    public String doGet(HttpServletRequest req, HttpServletResponse resp, Model model) throws ServletException, IOException {
        this.doPost(req, resp);
        return "bookshop";
    }

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
        req.setAttribute("products", productDAO.getAllProducts());
        req.setAttribute("warehouse", productDAO.getWarehouse());

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
        productDAO.removeFromCart(id);
        showCart(req, resp);
    }

    public void addToCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        productDAO.addToCart(id);
        showProduct(req, resp);
    }

    public void showCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products", productDAO.getAllProducts());
        req.setAttribute("cart", productDAO.getCart());
        req.setAttribute("page", "cart");
        req.getRequestDispatcher("/jsp/bookshop.jsp").forward(req, resp);
    }

    public void showProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("product", productDAO.getProductById(Integer.parseInt(req.getParameter("id"))));
        req.setAttribute("warehouseCount", productDAO.getCountFromWarehouse(Integer.parseInt(req.getParameter("id"))));
        req.setAttribute("id", req.getParameter("id"));
        req.setAttribute("page", "product");
        req.getRequestDispatcher("/jsp/bookshop.jsp").forward(req, resp);
    }

    public void addOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        productDAO.addOrder();
        req.setAttribute("products", productDAO.getAllProducts());
        req.setAttribute("orders", productDAO.getOrders());
        req.setAttribute("page", "orders");
        req.getRequestDispatcher("/jsp/bookshop.jsp").forward(req, resp);
    }

    public void deleteOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        productDAO.deleteOrders();
        showMainContent(req, resp);
    }
}