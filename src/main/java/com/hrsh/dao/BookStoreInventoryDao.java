package com.hrsh.dao;

import com.hrsh.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookStoreInventoryDao {
    private static List<Book> books = new ArrayList<Book>();

    public static List<Book> getBooks() {
        return books;
    }

    public static void setBook(List<Book> books) {
        BookStoreInventoryDao.books = books;
    }

    public static boolean addBook(Book book) {
        return books.add(book);
    }

    public static boolean removeBook(String isbn) {
        Optional<Book> bookOptional = books.stream().filter(book -> book.getIsbn().equalsIgnoreCase(isbn)).findFirst();
        return bookOptional.map(book -> books.remove(book)).orElse(false);
    }

    public static Optional<Book> findBookByISBN(String isbn) {
        return books.stream().filter(book -> book.getIsbn().equalsIgnoreCase(isbn)).findFirst();
    }

    public static boolean updateBook(Book oldBook, Book newBook) {
        int currBookIndex = books.indexOf(oldBook);
        if (currBookIndex == -1) {
            return false;
        }
        books.set(currBookIndex, newBook);
        return true;
    }

    public static boolean updateBookPrice(Book book, double newPrice) {
        int currBookIndex = books.indexOf(book);
        if (currBookIndex == -1) {
            return false;
        }
        books.get(currBookIndex).setPrice(newPrice);
        return true;
    }
}
