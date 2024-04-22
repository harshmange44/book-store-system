package com.hrsh.service.imp;

import com.hrsh.dao.BookStoreInventoryDao;
import com.hrsh.model.Book;
import com.hrsh.service.BookService;
import com.hrsh.strategy.fetch.book.BookFetchStrategy;
import com.hrsh.strategy.fetch.book.FetchStrategy;
import com.hrsh.strategy.fetch.book.PriceStrategy;
import com.hrsh.strategy.fetch.book.RatingStrategy;
import com.hrsh.utils.BookUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class BookServiceImpl implements BookService {

    private static BookService bookService = null;

    public static BookService getBookServiceInstance() {
        if (Objects.nonNull(bookService)) {
            return bookService;
        }

        bookService = new BookServiceImpl();
        return bookService;
    }

    @Override
    public boolean createBook(Book book) {
        BookUtils.validateIfBookAlreadyExists(book, BookStoreInventoryDao.getBooks());
        log.info("Book validated: {}", book);

        return BookStoreInventoryDao.addBook(book);
    }

    @Override
    public boolean deleteBook(String isbn) {
        if (getBook(isbn).isPresent()) {
            return BookStoreInventoryDao.removeBook(isbn);
        }
        return false;
    }

    @Override
    public Book updateBook(String isbn, Book book) {
        boolean isBookUpdated = false;
        Optional<Book> optionalBook = getBook(isbn);
        Book oldBook = null;
        if (optionalBook.isPresent()) {
            oldBook = optionalBook.get();
            isBookUpdated = BookStoreInventoryDao.updateBook(oldBook, book);
        }
        log.info("Book updated: {}", isBookUpdated);
        return isBookUpdated ? book : oldBook;
    }

    @Override
    public boolean updateBookCopiesToBeAdded(String isbn, int noOfCopiesToBeAdded) {
        boolean isBookUpdated = false;
        Optional<Book> optionalBook = getBook(isbn);
        Book book = null;
        if (optionalBook.isPresent()) {
            book = optionalBook.get();
            BookUtils.validateBookCopies(book, noOfCopiesToBeAdded, true);
            Book newBook = book.clone();
            newBook.setCopiesAvailable(newBook.getCopiesAvailable() + noOfCopiesToBeAdded);
            isBookUpdated = BookStoreInventoryDao.updateBook(book, newBook);
        }
        return isBookUpdated;
    }

    @Override
    public boolean updateBookCopiesToBeRemoved(String isbn, int noOfCopiesToBeRemoved) {
        boolean isBookUpdated = false;
        Optional<Book> optionalBook = getBook(isbn);
        Book book = null;
        if (optionalBook.isPresent()) {
            book = optionalBook.get();
            BookUtils.validateBookCopies(book, noOfCopiesToBeRemoved, false);
            Book newBook = book.clone();
            newBook.setCopiesAvailable(newBook.getCopiesAvailable() - noOfCopiesToBeRemoved);
            isBookUpdated = BookStoreInventoryDao.updateBook(book, newBook);
        }
        return isBookUpdated;
    }

    @Override
    public boolean updateBookPrice(String isbn, double newPrice) {
        boolean isBookUpdated = false;
        Optional<Book> optionalBook = getBook(isbn);
        Book book = null;
        if (optionalBook.isPresent()) {
            book = optionalBook.get();
            Book newBook = book.clone();
            newBook.setPrice(newPrice);
            isBookUpdated = BookStoreInventoryDao.updateBook(book, newBook);
        }
        return isBookUpdated;
    }

    @Override
    public Optional<Book> getBook(String isbn) {
        return BookStoreInventoryDao.findBookByISBN(isbn);
    }

    @Override
    public List<Book> getAllABooks(FetchStrategy fetchStrategy) {
        BookFetchStrategy bookFetchStrategy = null;

        switch (fetchStrategy) {
            case PRICE:
                bookFetchStrategy = new PriceStrategy();
                break;

            case RATING:
            default:
                bookFetchStrategy = new RatingStrategy();
                break;
        }

        return bookFetchStrategy.listBooks(BookStoreInventoryDao.getBooks());
    }
}
