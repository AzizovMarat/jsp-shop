<%@ page import="com.bft.bookshop.bftbookshop.entities.Product" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="ru" prefix="og: http://ogp.me/ns#">

<head>
    <% Product[] products = (Product[]) request.getAttribute("products"); %>
    <% String pageType = (String) request.getAttribute("page"); %>
    <%--    <% for (Product p : products) {--%>

    <%--    } %>--%>

    <%--    <script>--%>
    <%--        class Product {--%>
    <%--            constructor(productType, productName, author, price, img, about) {--%>
    <%--                this.productType = productType,--%>
    <%--                    this.productName = productName,--%>
    <%--                    this.author = author,--%>
    <%--                    this.price = price,--%>
    <%--                    this.img = img,--%>
    <%--                    this.about = about--%>
    <%--            }--%>
    <%--        }--%>

    <%--        let products = [];--%>
    <%--        <% for (int i = 0; i < products.length; i++) { %>--%>
    <%--            products[<%= i %>] = new Product(`<%= products[i].getProductType() %>`, `<%= products[i].getProductName() %>`,--%>
    <%--                `<%= products[i].getAuthor() %>`, `<%= products[i].getPrice() %>`,--%>
    <%--                `<%= products[i].getImg() %>`, `<%= products[i].getAbout() %>`);--%>
    <%--        <% } %>--%>
    <%--        module.exports = products;--%>
    <%--    </script>--%>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="BookShop, БФТ">
    <meta name="description" content="Простой одностраничный SPA-сайт">
    <meta name="author" content="Marat Azizov">
    <meta name="docsearch:language" content="ru">
    <title>
        <% if ("bookshop".equals(pageType)) { %>
        БФТ - BookShop
        <% } else if ("product".equals(pageType)) { %>
        БФТ - О товаре
        <% } else if ("cart".equals(pageType)) { %>
        БФТ - Корзина
        <% } %>
    </title>
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css"
          integrity="sha384-r4NyP46KrjDleawBgD5tp8Y7UzmLA05oM1iAEQ17CSuDqnUK2+k9luXQOfXJCJ4I" crossorigin="anonymous">
    <link rel="stylesheet" href="https://bootstrap5.ru/css/docs.css">
    <!-- MyStyle -->
    <link rel="stylesheet" href="lib/style.css">
</head>

<body>
<header>
    <div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm">
        <h5 class="my-0 mr-md-auto font-weight-normal" id="mainTitle">
            <% if ("bookshop".equals(pageType)) { %>
            БФТ - BookShop
            <% } else if ("product".equals(pageType)) { %>
            БФТ - О товаре
            <% } else if ("cart".equals(pageType)) { %>
            БФТ - Корзина
            <% } %>
        </h5>
        <nav class="my-2 my-md-0 mr-md-3">
            <a class="p-2 text-dark" href="#" onclick="showCartContent()">
                <img class="bd-placeholder-img" src="../img/cart.png" alt="cart" width="25" height="25">
            </a>
        </nav>
        <a class="btn btn-outline-primary" href="#">Войти</a>
    </div>
</header>

<main>

    <div class="album py-5 bg-light">
        <div class="container">

            <div class="row mb-2" id="mainContent" <% if (!"bookshop".equals(pageType)) { %> hidden="" <% } %>>
                <% if ("bookshop".equals(pageType)) { %>
                <% for (int i = 0; i < products.length; i++) { %>
                <div class="col-xl-6">
                    <div class="row g-0 border rounded overflow-hidden flex-md-row mb-4 product-card h-md-250 position-relative">
                        <div class="col p-4 d-flex flex-column">
                            <div class="row-auto d-flex flex-column">
                                <div class="mb-auto">
                                    <strong class="d-inline-block mb-2 text-primary">
                                        <%= products[i].getProductType() %>
                                    </strong>
                                    <h3 class="mb-0">
                                        <%= products[i].getProductName() %>
                                    </h3>
                                    <div class="mb-1 text-muted">
                                        <%= products[i].getAuthor() %>
                                    </div>

                                    <a href="#" class="stretched-link" onclick="showProductContent(<%= i %>)">
                                        О товаре
                                    </a>
                                </div>
                                <div class="d-flex align-items-end"><%= products[i].getPrice() %> ₽</div>
                            </div>
                        </div>

                        <div class="col-auto d-none d-md-block">
                            <img class="bd-placeholder-img" src="<%= products[i].getImg() %>"
                                 alt="<%= products[i].getProductName() %>" width="299" height="299">
                        </div>
                    </div>
                </div>
                <% } %>
                <% } %>
            </div>

            <div class="row mb-2" id="productContent" <% if (!"product".equals(pageType)) { %> hidden="" <% } %>>
                <% if ("product".equals(pageType)) { %>
                <% int id = Integer.parseInt((String) request.getAttribute("id")); %>
                <div class="row g-0 border rounded overflow-hidden flex-md-row h-md-250 position-relative">
                    <div class="col d-flex flex-column px-4 pt-4">
                        <div class="row-auto d-flex flex-column">
                            <div class="">
                                <strong class="d-inline-block mb-2 text-primary">
                                    <%= products[id].getProductType() %>
                                </strong>
                                <h3 class="mb-0">
                                    <%= products[id].getProductName() %>
                                </h3>
                                <div class="mb-1 text-muted">
                                    <%= products[id].getAuthor() %>
                                </div>
                                <div class="mb-0">
                                    <%= products[id].getAbout() %>
                                </div>
                            </div>

                            <div class="d-flex justify-content-between my-2">
                                <div class=""><%= products[id].getPrice() %> ₽</div>
                                <button type="button" class="btn btn-primary" onclick="addInCart(<%= id %>)">
                                    Добавить в корзину
                                </button>
                                <button type="button" class="btn btn-secondary" onclick="showMainContent()">Close
                                </button>
                            </div>
                        </div>
                    </div>

                    <div class="col-auto d-none d-xl-block">
                        <img class="bd-placeholder-img" src="<%= products[id].getImg() %>"
                             alt="<%= products[id].getProductName() %>" width="350" height="350">
                    </div>
                </div>
                <% } %>
            </div>

            <div class="row mb-2" id="cartContent" <% if (!"cart".equals(pageType)) { %> hidden="" <% } %>>
                <div class="container" id="bodyCartContent">
                    <% List<int[]> cart = (List<int[]>) request.getAttribute("cart"); %>
                    <div class="row" id="notEmptyCartContent" <% if (cart == null || cart.isEmpty()) { %>
                         hidden="" <% } %>>
                        <div class="col border rounded border-primary bg-primary">
                        </div>
                        <div class="col-xxl-6 col-xl-4 col-4 border rounded border-primary bg-primary text-white text-center text-truncate">
                            Описание товара
                        </div>
                        <div class="col border rounded border-primary bg-primary text-white text-center text-truncate">
                            Цена за штуку
                        </div>
                        <div class="col border rounded border-primary bg-primary text-white text-center text-truncate">
                            Количество
                        </div>
                        <div class="col border rounded border-primary bg-primary text-white text-center text-truncate">
                            Цена
                        </div>
                    </div>
                    <% if (cart != null && !cart.isEmpty()) { %>
                    <% for (int i = 0; i < cart.size(); i++) { %>
                    <div class="row" id="<%= i %>">
                        <div class="col border rounded border-light-dark bg-light-dark text-truncate">
                            <a href="#" onclick=removeFromCart(<%= i %>)>Удалить</a>
                        </div>
                        <div class="col-xxl-6 col-xl-4 col-4 border rounded border-light-dark bg-light-dark text-truncate">
                            <%= products[cart.get(i)[0]].getProductName() %>
                        </div>
                        <div class="col border rounded border-light-dark bg-light-dark text-truncate">
                            <%= products[cart.get(i)[0]].getPrice() %>
                        </div>
                        <div class="col border rounded border-light-dark bg-light-dark text-truncate" id="count">
                            <%= cart.get(i)[1] %>
                        </div>
                        <div class="col border rounded border-light-dark bg-light-dark text-truncate">
                            <%= products[cart.get(i)[0]].getPrice() * cart.get(i)[1] %>
                        </div>
                    </div>
                    <% } %>
                    <% } %>

                    <div class="row justify-content-center" id="emptyCartContent"
                            <% if (cart != null && !cart.isEmpty()) { %> hidden="" <% } %>>
                        <div class="border rounded border-primary col-4 bg-primary text-white text-center">
                            Добавьте товар в корзину
                        </div>
                    </div>
                </div>
                <div class="container">
                    <div class="row col-3 mx-auto mt-5">
                        <button type="button" class="btn btn-primary" onclick="showMainContent()">Продолжить покупки
                        </button>
                    </div>
                </div>
            </div>

        </div>
    </div>

</main>

<footer class="text-muted py-5">
    <div class="container">
        <p class="float-right mb-1">
            <a href="#">Наверх</a>
        </p>
    </div>
</footer>

<script src="../lib/withJSP.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/js/bootstrap.bundle.min.js"></script>
</body>

</html>