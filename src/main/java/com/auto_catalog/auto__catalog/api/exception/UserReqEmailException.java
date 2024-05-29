package com.auto_catalog.auto__catalog.api.exception;

public class UserReqEmailException extends RuntimeException {

    private final String message;

    public UserReqEmailException(String message){
        this.message = message;
    }
    @Override
    public String toString() {
        return " Registration problem! We already have user with email:  " + message ;
    }
}

