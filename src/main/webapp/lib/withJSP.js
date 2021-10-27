'use strict'

document.addEventListener("DOMContentLoaded", function () {
    const myShowOnlyInWarehouse = document.querySelector('.myShowOnlyInWarehouse');
    if (myShowOnlyInWarehouse != null) {
        myShowOnlyInWarehouse.addEventListener('change', function () {
            showMainContent(myShowOnlyInWarehouse.checked);
        });
    }
});

function addOrder() {
    location.href = "/main?method=addOrder";
}

function deleteOrders() {
    location.href = "/main?method=deleteOrders";
}

function showProductContent(index) {
    location.href = "/main?method=showProduct&id=" + index;
}

function showMainContent(showOnlyInWarehouse) {
    if (showOnlyInWarehouse == null) {
        location.href = "/main";
    } else {
        location.href = "/main?method=showMainContent&onlyInWarehouse=" + showOnlyInWarehouse;
    }
}

function addToCart(index) {
    location.href = "/main?method=addToCart&id=" + index;
}

function showCartContent() {
    location.href = "/main?method=showCart";
}

function removeFromCart(index) {
    location.href = "/main?method=removeFromCart&id=" + index;
}