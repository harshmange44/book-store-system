package com.hrsh.service;

import com.hrsh.model.Cart;

public interface CartService {
    boolean addBookToCart(String emailId, String bookIsbn, int quantity);
    boolean removeBookFromCart(String emailId, String bookIsbn, int explicitQuantity);
    boolean updateBookQuantityInCart(String emailId, String bookIsbn, int quantity);
    Cart getCart(String emailId);
    Cart createNewCart();
}
