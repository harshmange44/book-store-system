package com.hrsh.strategy.fetch.book;

import com.hrsh.model.Book;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PriceStrategy implements BookFetchStrategy {
    @Override
    public List<Book> listBooks(List<Book> bookList) {
        Collections.sort(bookList, new Comparator<Book>() {
            @Override
            public int compare(Book book1, Book book2) {
                return Double.compare(book2.getPrice(), book1.getPrice());
            }
        });
        return bookList;
    }
}
