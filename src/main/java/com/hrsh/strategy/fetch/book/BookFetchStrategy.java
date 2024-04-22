package com.hrsh.strategy.fetch.book;

import com.hrsh.model.Book;

import java.util.List;

public interface BookFetchStrategy {
    List<Book> listBooks(List<Book> bookList);
}
