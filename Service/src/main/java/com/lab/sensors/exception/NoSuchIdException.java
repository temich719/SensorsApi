package com.lab.sensors.exception;

public class NoSuchIdException extends Exception {

    private final String message;

    public NoSuchIdException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
