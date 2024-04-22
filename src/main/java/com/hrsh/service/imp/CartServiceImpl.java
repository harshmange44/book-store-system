package com.hrsh.service.imp;

import com.hrsh.dao.CartDao;
import com.hrsh.model.Book;
import com.hrsh.model.Cart;
import com.hrsh.model.User;
import com.hrsh.service.BookService;
import com.hrsh.service.CartService;
import com.hrsh.service.UserService;
import com.hrsh.utils.BookUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class CartServiceImpl implements CartService {
    private static UserService userService = UserServiceImpl.getUserServiceInstance();
    private static BookService bookService = BookServiceImpl.getBookServiceInstance();
    private static CartService cartService = new CartServiceImpl(userService, bookService);

    public static CartService getCartServiceInstance() {
        if (Objects.nonNull(cartService)) {
            return cartService;
        }

        cartService = new CartServiceImpl(userService, bookService);
        return cartService;
    }

    public CartServiceImpl(UserService userService, BookService bookService) {
        CartServiceImpl.userService = userService;
        CartServiceImpl.bookService = bookService;
    }

    @Override
    public boolean addBookToCart(String emailId, String bookIsbn, int quantity) {
        Optional<User> optionalUser = userService.getUser(emailId);
        Optional<Book> optionalBook = bookService.getBook(bookIsbn);

        if (optionalUser.isPresent() && optionalBook.isPresent()) {
            Book book = optionalBook.get();
            Cart cart = optionalUser.get().getCart();
            BookUtils.validateBookCopies(book, quantity, false);
            log.info("Validated book copies");

            bookService.updateBookCopiesToBeRemoved(book.getIsbn(), quantity);
            log.info("Removed {} copies for the book with isbn: {}", quantity, book.getIsbn());
            return CartDao.addBookToCart(cart, optionalBook.get(), quantity);
        }
        return false;
    }

    @Override
    public boolean removeBookFromCart(String emailId, String bookIsbn, int explicitQuantity) {
        Optional<User> optionalUser = userService.getUser(emailId);
        Optional<Book> optionalBook = bookService.getBook(bookIsbn);

        if (optionalUser.isPresent() && optionalBook.isPresent()) {
            Book book = optionalBook.get();
            Cart cart = optionalUser.get().getCart();
            Map<UUID, Integer> bookQtyMap = cart.getBookQtyMap();
            int quantity = bookQtyMap.get(book.getId());
            if (explicitQuantity != 0) {
                quantity = explicitQuantity;
            }
            bookService.updateBookCopiesToBeAdded(book.getIsbn(), quantity);
            return CartDao.removeBookFromCart(cart, optionalBook.get(), quantity);
        }
        return false;
    }

    @Override
    public boolean updateBookQuantityInCart(String emailId, String bookIsbn, int quantity) {
        Optional<User> optionalUser = userService.getUser(emailId);
        Optional<Book> optionalBook = bookService.getBook(bookIsbn);

        if (optionalUser.isPresent() && optionalBook.isPresent()) {
            Book book = optionalBook.get();
            Cart cart = optionalUser.get().getCart();
            Map<UUID, Integer> bookQtyMap = cart.getBookQtyMap();
            int currQuantity = bookQtyMap.get(book.getId());
            if (currQuantity > quantity) {
                return removeBookFromCart(emailId, bookIsbn, quantity);
            } else {
                return addBookToCart(emailId, bookIsbn, quantity - currQuantity);
            }
        }
        return false;
    }

    @Override
    public Cart getCart(String emailId) {
        Optional<User> optionalUser = userService.getUser(emailId);
        return optionalUser.map(User::getCart).orElse(null);
    }

    @Override
    public Cart createNewCart() {
        Cart cart = new Cart(UUID.randomUUID(), new HashMap<>(), 0);
        CartDao.addCart(cart);
        log.info("New cart init & added: {}", cart);
        return cart;
    }
}
