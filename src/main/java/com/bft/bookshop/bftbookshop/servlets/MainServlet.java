package com.bft.bookshop.bftbookshop.servlets;

import com.bft.bookshop.bftbookshop.entities.ProductDAO;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/main")
public class MainServlet extends HttpServlet {
    private ProductDAO productDAO;
    private final List<int[]> cart = new ArrayList<>(); // int[0] - index of products, int[1] - his quantity

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDAO = new ProductDAO();
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
        req.setAttribute("products", productDAO.getAllProducts());
        req.setAttribute("page", "bookshop");
        req.getRequestDispatcher("/jsp/bookshop.jsp").forward(req, resp);
    }

    public void removeFromCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        cart.remove(id);
        showCart(req, resp);
    }

    public void addInCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        for (int[] ints : cart) {
            if (ints[0] == id) {
                ints[1] += 1;
                showProduct(req, resp);
                return;
            }
        }

        cart.add(new int[]{id, 1});

        showProduct(req, resp);
    }
    public void showCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products", productDAO.getAllProducts());
        req.setAttribute("cart", cart);
        req.setAttribute("page", "cart");
        req.getRequestDispatcher("/jsp/bookshop.jsp").forward(req, resp);
    }

    public void showProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products", productDAO.getAllProducts());
        req.setAttribute("id", req.getParameter("id"));
        req.setAttribute("page", "product");
        req.getRequestDispatcher("/jsp/bookshop.jsp").forward(req, resp);
    }
}