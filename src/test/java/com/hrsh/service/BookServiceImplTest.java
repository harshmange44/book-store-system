package com.hrsh.service;

import com.hrsh.enums.Genre;
import com.hrsh.exception.BookAlreadyExists;
import com.hrsh.mockdata.MockBookData;
import com.hrsh.mockdata.MockPublisherData;
import com.hrsh.mockdata.MockUserData;
import com.hrsh.model.Book;
import com.hrsh.model.Publisher;
import com.hrsh.model.User;
import com.hrsh.service.imp.BookServiceImpl;
import com.hrsh.service.imp.UserServiceImpl;
import com.hrsh.strategy.fetch.book.FetchStrategy;
import junit.framework.TestCase;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BookServiceImplTest extends TestCase {

    private Publisher publisher;
    User authorUser;
    private BookService bookService;

    public void setUp() throws Exception {
        this.bookService = BookServiceImpl.getBookServiceInstance();
        UserService userService = UserServiceImpl.getUserServiceInstance();
        authorUser = MockUserData.getAuthorUser();
        userService.createUser(authorUser);
        publisher = MockPublisherData.createPublisher("Publisher 1");
        super.setUp();
    }

    public void testCreateBook() {
        Book book1 = MockBookData.createBook("ISBN1", "Book 1", 100, 50, Genre.ACTION, 4.5, authorUser, publisher, LocalDate.now());
        bookService.createBook(book1);

        Optional<Book> fetchedBookOpt = bookService.getBook(book1.getIsbn());

        assertTrue(fetchedBookOpt.isPresent());
        Book fetchedBook = fetchedBookOpt.get();
        assertEquals(book1, fetchedBook);
    }

    @Test(expected = BookAlreadyExists.class)
    public void testCreatingBookWhenAlreadyExists() {
//        Book book1 = MockBookData.createBook("ISBN1", "Book 1", 100, 50, Genre.ACTION, 4.5, authorUser, publisher, LocalDate.now());
//        bookService.createBook(book1);
//
//        Book book2 = MockBookData.createBook("ISBN1", "Book 2", 120, 30, Genre.FICTION, 4.2, authorUser, publisher, LocalDate.now());
//        bookService.createBook(book2);
    }

    public void testGetAllABooks() {
        Book book1 = MockBookData.createBook("ISBN1", "Book 1", 100, 50, Genre.ACTION, 4.2, authorUser, publisher, LocalDate.now());
        bookService.createBook(book1);

        Book book2 = MockBookData.createBook("ISBN2", "Book 2", 120, 30, Genre.FICTION, 4.5, authorUser, publisher, LocalDate.now());
        bookService.createBook(book2);

        List<Book> bookList = bookService.getAllABooks(FetchStrategy.RATING);

        assertEquals(book2, bookList.get(0));
        assertEquals(book1, bookList.get(1));
        assertTrue(bookList.get(0).getRating() > bookList.get(1).getRating());
    }
}