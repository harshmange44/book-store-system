package com.hrsh.dao;

import com.hrsh.model.Book;
import com.hrsh.model.Cart;

import java.util.*;

public class CartDao {
    private static List<Cart> carts = new ArrayList<>();

    public static List<Cart> getCarts() {
        return carts;
    }

    public static void setCarts(List<Cart> carts) {
        CartDao.carts = carts;
    }

    public static boolean addBookToCart(Cart cart, Book book, int quantity) {
        Map<UUID, Integer> bookQtyMap = cart.getBookQtyMap();
        if (bookQtyMap.containsKey(book.getId())) {
            bookQtyMap.put(book.getId(), bookQtyMap.get(book.getId()) + quantity);
        } else {
            bookQtyMap.put(book.getId(), quantity);
        }
        cart.setBookQtyMap(bookQtyMap);
        int currCartIndex = carts.indexOf(cart);
        if (currCartIndex == -1) {
            return false;
        }
        double orderValue = calculateCartOrderValueDiff(book, quantity);
        cart.setOrderValue(cart.getOrderValue() + orderValue);
        carts.set(currCartIndex, cart);
        return true;
    }

    public static boolean removeBookFromCart(Cart cart, Book book, int quantity) {
        Map<UUID, Integer> bookQtyMap = cart.getBookQtyMap();
        bookQtyMap.remove(book.getId());
        cart.setBookQtyMap(bookQtyMap);
        int currCartIndex = carts.indexOf(cart);
        if (currCartIndex == -1) {
            return false;
        }
        double orderValue = calculateCartOrderValueDiff(book, quantity);
        cart.setOrderValue(cart.getOrderValue() - orderValue);
        carts.set(currCartIndex, cart);
        return true;
    }

    private static double calculateCartOrderValueDiff(Book book, int quantity) {
        return book.getPrice()*quantity;
    }

    public static void addCart(Cart cart) {
        carts.add(cart);
    }
}
