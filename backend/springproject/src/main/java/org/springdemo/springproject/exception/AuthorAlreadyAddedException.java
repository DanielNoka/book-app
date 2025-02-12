package org.springdemo.springproject.exception;

public class AuthorAlreadyAddedException extends RuntimeException {
    public AuthorAlreadyAddedException(String message) {
        super(message);
    }
}

