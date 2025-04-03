package org.springdemo.springproject.util;

public class Constants {

    // Private constructor to prevent instantiation
    private Constants() {
        throw new UnsupportedOperationException("Cannot instantiate this class");
    }

    public static  final String OK = "OK";

    public static  final String FAIL = "FAIL";

    public static  final String CREATED = "CREATED";

    public static  final String UPDATED = "UPDATED";

    public static  final int PAGE_SIZE = 3;

    public static final String ADMIN = "ADMIN";

    public static final String USER = "USER";

    public static final String INVALID_TOKEN = "INVALID_TOKEN";

    public static final String TOKEN_SUCCESS = "TOKEN_SUCCESS";

    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public static final int OTP_EXPIRATION_TIME = 10;

    public static final String SUCCESS = "SUCCESS";
}
