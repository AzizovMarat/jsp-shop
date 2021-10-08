'use strict'

function showProductContent(index) {
    location.href="/main?method=showProduct&id=" + index;
}

function showMainContent() {
    location.href="/main";
}

function addInCart(index) {
    location.href="/main?method=addInCart&id=" + index;
}

function showCartContent() {
    location.href="/main?method=showCart";
}

function removeFromCart(index) {
    location.href="/main?method=removeFromCart&id=" + index;
}