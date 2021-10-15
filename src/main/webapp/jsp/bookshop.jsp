<%@ page import="com.bft.bookshop.bftbookshop.entities.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="ru" prefix="og: http://ogp.me/ns#">

<head>
    <% Product[] products = (Product[]) request.getAttribute("products"); %>
    <% String pageType = (String) request.getAttribute("page"); %>

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
        <% } else if ("orders".equals(pageType)) { %>
        БФТ - Заказы
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
            <% } else if ("orders".equals(pageType)) { %>
            БФТ - Заказы
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
            <% if ("bookshop".equals(pageType)) { %>
            <% boolean onlyInWarehouse = (boolean) request.getSession().getAttribute("onlyInWarehouse"); %>
            <div class="row mb-2" id="mainContent">
                <div class="form-check mx-3">
                    <% if (onlyInWarehouse) { %>
                    <input class="form-check-input myShowOnlyInWarehouse" type="checkbox" value=""
                           id="flexCheckChecked" checked>
                    <label class="form-check-label" for="flexCheckChecked">Только в наличии</label>
                    <% } else { %>
                    <input class="form-check-input myShowOnlyInWarehouse" type="checkbox" value=""
                           id="flexCheckDefault">
                    <label class="form-check-label" for="flexCheckDefault">Только в наличии</label>
                    <% } %>
                </div>
                <% List<int[]> warehouse = (List<int[]>) request.getAttribute("warehouse"); %>
                <% if (warehouse != null && !warehouse.isEmpty()) { %>
                <% for (int i = 0; i < products.length; i++) { %>
                <% if (onlyInWarehouse && warehouse.get(i)[1] == 0) continue; %>
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

                                    <a href="#" class="stretched-link"
                                       onclick="showProductContent(<%= products[i].getProduct_id() %>)">
                                        О товаре
                                    </a>
                                </div>
                                <div class="d-flex align-items-end"><%= products[i].getPrice() %> ₽</div>
                                <div class="text-muted">На складе - <%= warehouse.get(i)[1] %> шт.</div>
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
            <% } %>

            <% if ("product".equals(pageType)) { %>
            <div class="row mb-2" id="productContent">
                <% Product product = (Product) request.getAttribute("product"); %>
                <div class="row g-0 border rounded overflow-hidden flex-md-row h-md-250 position-relative">
                    <div class="col d-flex flex-column px-4 pt-4">
                        <div class="row-auto d-flex flex-column">
                            <div class="">
                                <strong class="d-inline-block mb-2 text-primary">
                                    <%= product.getProductType() %>
                                </strong>
                                <h3 class="mb-0">
                                    <%= product.getProductName() %>
                                </h3>
                                <div class="mb-1 text-muted">
                                    <%= product.getAuthor() %>
                                </div>
                                <div class="mb-0">
                                    <%= product.getAbout() %>
                                </div>
                            </div>

                            <div class="d-flex justify-content-between my-2">
                                <div class=""><%= product.getPrice() %> ₽</div>
                                <div class="text-muted">На складе - <%= request.getAttribute("warehouseCount") %>шт.
                                </div>
                                <button type="button" class="btn btn-primary"
                                        onclick="addInCart(<%= product.getProduct_id() %>)"
                                        <% if((int) request.getAttribute("warehouseCount") == 0) { %>disabled<% } %>>
                                    Добавить в корзину
                                </button>
                                <button type="button" class="btn btn-secondary" onclick="showMainContent()">
                                    Закрыть
                                </button>
                            </div>
                        </div>
                    </div>

                    <div class="col-auto d-none d-xl-block">
                        <img class="bd-placeholder-img" src="<%= product.getImg() %>"
                             alt="<%= product.getProductName() %>" width="350" height="350">
                    </div>
                </div>
            </div>
            <% } %>

            <% if ("cart".equals(pageType)) { %>
            <div class="row mb-2" id="cartContent">
                <div class="container" id="bodyCartContent">
                    <% List<int[]> cart = (List<int[]>) request.getAttribute("cart"); %>
                    <% if (cart != null && !cart.isEmpty()) { %>
                    <div class="row" id="notEmptyCartContent">
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
                    <% for (int i = 0; i < cart.size(); i++) { %>
                    <% int id = cart.get(i)[0] - 1; %>
                    <div class="row" id="<%= i %>">
                        <div class="col border rounded border-light-dark bg-light-dark text-truncate text-center">
                            <a href="#" onclick=removeFromCart(<%= cart.get(i)[0] %>)>Удалить</a>
                        </div>
                        <div class="col-xxl-6 col-xl-4 col-4 border rounded border-light-dark bg-light-dark text-truncate">
                            <%= products[id].getProductName() %>
                        </div>
                        <div class="col border rounded border-light-dark bg-light-dark text-truncate text-center">
                            <%= products[id].getPrice() %>
                        </div>
                        <div class="col border rounded border-light-dark bg-light-dark text-truncate text-center"
                             id="count">
                            <%= cart.get(i)[1] %>
                        </div>
                        <div class="col border rounded border-light-dark bg-light-dark text-truncate text-center">
                            <%= products[id].getPrice() * cart.get(i)[1] %>
                        </div>
                    </div>
                    <% } %>
                    <% } else { %>
                    <div class="row justify-content-center" id="emptyCartContent">
                        <div class="border rounded border-primary col-4 bg-primary text-white text-center">
                            Добавьте товар в корзину
                        </div>
                    </div>
                    <% } %>
                </div>
                <div class="container">
                    <div class="row mt-5 justify-content-center">
                        <div class="col-2">
                            <button type="button" class="btn btn-primary btn-block" onclick="showMainContent()">
                                Продолжить покупки
                            </button>
                        </div>
                        <% if (cart != null && !cart.isEmpty()) { %>
                        <div class="col-2">
                            <button type="button" class="btn btn-outline-primary btn-block" onclick="addOrder()">
                                Оформить заказ
                            </button>
                        </div>
                        <% } %>
                    </div>
                </div>
            </div>
            <% } %>
            <% if ("orders".equals(pageType)) { %>
            <div class="row mb-2">
                <div class="container">
                    <% Map<Integer, List<int[]>> orders = (Map<Integer, List<int[]>>) request.getAttribute("orders"); %>
                    <% if (orders != null && !orders.isEmpty()) { %>
                    <div class="row">
                        <div class="col border rounded border-primary bg-primary text-white text-truncate text-center">
                            ID
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
                    <% for (Map.Entry<Integer, List<int[]>> entry : orders.entrySet()) { %>
                    <% for (int[] ints : entry.getValue()) { %>
                    <% int id_products = ints[0] - 1; %>
                    <% int count = ints[1]; %>
                    <div class="row">
                        <div class="col border rounded border-light-dark bg-light-dark text-truncate text-center">
                            <%= entry.getKey() %>
                        </div>
                        <div class="col-xxl-6 col-xl-4 col-4 border rounded border-light-dark bg-light-dark text-truncate">
                            <%= products[id_products].getProductName() %>
                        </div>
                        <div class="col border rounded border-light-dark bg-light-dark text-truncate text-center">
                            <%= products[id_products].getPrice() %>
                        </div>
                        <div class="col border rounded border-light-dark bg-light-dark text-truncate text-center">
                            <%= count %>
                        </div>
                        <div class="col border rounded border-light-dark bg-light-dark text-truncate text-center">
                            <%= products[id_products].getPrice() * count %>
                        </div>
                    </div>
                    <% } %>
                    <% } %>
                    <% } else { %>
                    <div class="row justify-content-center">
                        <div class="border rounded border-primary col-4 bg-primary text-white text-center">
                            Оформите заказ
                        </div>
                    </div>
                    <% } %>
                </div>
                <div class="container">
                    <div class="row mt-5 justify-content-center">
                        <div class="col-2">
                            <button type="button" class="btn btn-primary btn-block" onclick="showMainContent()">
                                Продолжить покупки
                            </button>
                        </div>
                        <div class="col-2">
                            <button type="button" class="btn btn-secondary btn-block" onclick="deleteOrders()">
                                Удалить все заказы
                            </button>
                        </div>
                    </div>

                </div>
            </div>
            <% } %>
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