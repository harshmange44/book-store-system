package com.hrsh;

import com.hrsh.enums.Genre;
import com.hrsh.enums.PaymentMethod;
import com.hrsh.enums.PaymentStatus;
import com.hrsh.mockdata.MockBookData;
import com.hrsh.mockdata.MockPublisherData;
import com.hrsh.mockdata.MockUserData;
import com.hrsh.model.Book;
import com.hrsh.model.Order;
import com.hrsh.model.Publisher;
import com.hrsh.model.User;
import com.hrsh.service.BookService;
import com.hrsh.service.CartService;
import com.hrsh.service.OrderService;
import com.hrsh.service.UserService;
import com.hrsh.service.imp.BookServiceImpl;
import com.hrsh.service.imp.CartServiceImpl;
import com.hrsh.service.imp.OrderServiceImpl;
import com.hrsh.service.imp.UserServiceImpl;
import com.hrsh.strategy.fetch.book.FetchStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.BasicConfigurator;

import java.time.LocalDate;
import java.util.List;

@Slf4j
public class BookStoreApplication {
    public static void main(String[] args) {

        BasicConfigurator.configure();

        UserService userService = UserServiceImpl.getUserServiceInstance();
        BookService bookService = BookServiceImpl.getBookServiceInstance();
        CartService cartService = CartServiceImpl.getCartServiceInstance();
        OrderService orderService = OrderServiceImpl.getOrderServiceInstance();


        User adminUser = MockUserData.getAdminUser();
        userService.createUser(adminUser);

        User freeUser = MockUserData.getFreeUser();
        userService.createUser(freeUser);

        User premiumUser = MockUserData.getPremiumUser();
        userService.createUser(premiumUser);

        User authorUser = MockUserData.getAuthorUser();
        userService.createUser(authorUser);
        
        log.info("All Created Users: {}", userService.getAllUser());

        Publisher publisher = MockPublisherData.createPublisher("Publisher 1");
        Book book1 = MockBookData.createBook("ISBN1", "Book 1", 100, 50, Genre.ACTION, 4.5, authorUser, publisher, LocalDate.now());
        bookService.createBook(book1);

        Book book2 = MockBookData.createBook("ISBN2", "Book 2", 120, 40, Genre.FICTION, 4.1, authorUser, publisher, LocalDate.now());
        bookService.createBook(book2);

        log.info("All Created Books: {}", bookService.getAllABooks(FetchStrategy.RATING));

        cartService.addBookToCart(freeUser.getEmailId(), book1.getIsbn(), 2);
        log.info("[Added book1 x 2] Cart: {}", cartService.getCart(freeUser.getEmailId()));
        cartService.addBookToCart(freeUser.getEmailId(), book2.getIsbn(), 1);
        log.info("[Added book2 x 1] Cart: {}", cartService.getCart(freeUser.getEmailId()));

        orderService.initiateOrder(freeUser.getEmailId(), freeUser.getCart(), PaymentMethod.CREDIT_CARD);
        List<Order> orders = userService.getUserOrders(freeUser.getEmailId());
        log.info("[Pre-payment] Order: {}", orders);
        orderService.updatePaymentStatus(orders.get(0).getId(), PaymentStatus.COMPLETED);
        orderService.executeOrder(freeUser.getEmailId(), freeUser.getCart());
        log.info("[Post-payment] Order: {}", userService.getUserOrders(freeUser.getEmailId()));
    }
}
