package com.hrsh.exception;

public class InvalidBookCopies extends RuntimeException {
    public InvalidBookCopies(String message) {
        super(message);
    }
}
