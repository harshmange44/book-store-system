package com.hrsh.utils;

import com.hrsh.exception.BookAlreadyExists;
import com.hrsh.exception.InvalidBookCopies;
import com.hrsh.model.Book;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class BookUtils {
    public static void validateIfBookAlreadyExists(Book book, List<Book> books) {
        if (books.stream().anyMatch(bookItr -> bookItr.getIsbn().equalsIgnoreCase(book.getIsbn()))) {
            log.error("Book already exists: {}", book);
            throw new BookAlreadyExists(String.format("Book with the ISBN: %s already exists", book.getIsbn()));
        }
    }

    public static void validateBookCopies(Book book, int noOfCopiesToBeAddedOrRemoved, boolean isAddition) {
        if (noOfCopiesToBeAddedOrRemoved < 0) {
            log.error("Book copies to be added or removed can't be negative (< 0): {}, book: {}", noOfCopiesToBeAddedOrRemoved, book);
            throw new InvalidBookCopies(String.format("Book copies to be added or removed can't be negative: %s", noOfCopiesToBeAddedOrRemoved));
        }

        if (!isAddition) {
            if (book.getCopiesAvailable() - noOfCopiesToBeAddedOrRemoved < 0) {
                log.error("Book copies to be removed can't be more than current available book quantity. Current available book qty: {}, No. of copies to be removed: {}, book: {}", book.getCopiesAvailable(), noOfCopiesToBeAddedOrRemoved, book);
                throw new InvalidBookCopies(String.format("Book copies to be removed can't be more than current available book quantity. Current available book qty: %s, No. of copies to be removed: %s", book.getCopiesAvailable(), noOfCopiesToBeAddedOrRemoved));
            }
        }
    }
}
