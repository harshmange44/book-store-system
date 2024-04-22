package com.hrsh.mockdata;

import com.hrsh.enums.Genre;
import com.hrsh.model.Book;
import com.hrsh.model.Publisher;
import com.hrsh.model.User;

import java.time.LocalDate;
import java.util.UUID;

public class MockBookData {
    public static Book createBook(String isbn, String title, double price, int copiesAvailable, Genre genre, double rating, User author, Publisher publisher, LocalDate publishedAt) {
        Book book = new Book(isbn);
        book.setId(UUID.randomUUID());
        book.setTitle(title);
        book.setPrice(price);
        book.setCopiesAvailable(copiesAvailable);
        book.setGenre(genre);
        book.setRating(rating);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setPublishedAt(publishedAt);
        return book;
    }
}
