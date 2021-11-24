package com.marsrover.rover.exception;


public class BadRequestException extends Exception {

    public BadRequestException(String errorMsg) {
        super(errorMsg);
    }
}
