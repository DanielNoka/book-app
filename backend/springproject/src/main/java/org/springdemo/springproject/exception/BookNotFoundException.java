package org.springdemo.springproject.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) {
        super("The book with id " + id + " was not found");
    }
}
