package com.auto_catalog.auto__catalog.api.exception;

public class DeleteNotFoundException extends RuntimeException  {
    private final String message;

    public DeleteNotFoundException(String message){
        this.message = message;
    }
    @Override
    public String toString() {
        return " Content not found " + message;
    }
}
