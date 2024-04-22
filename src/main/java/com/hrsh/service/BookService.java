package com.hrsh.service;

import com.hrsh.model.Book;
import com.hrsh.strategy.fetch.book.FetchStrategy;

import java.util.List;
import java.util.Optional;

public interface BookService {
    boolean createBook(Book book);
    boolean deleteBook(String isbn);
    Book updateBook(String isbn, Book book);
    boolean updateBookCopiesToBeAdded(String isbn, int noOfCopiesToBeAdded);
    boolean updateBookCopiesToBeRemoved(String isbn, int noOfCopiesToBeRemoved);
    boolean updateBookPrice(String isbn, double newPrice);
    Optional<Book> getBook(String isbn);
    List<Book> getAllABooks(FetchStrategy fetchStrategy);
}
