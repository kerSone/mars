package com.marsrover.rover.error;


public class BadRequestException extends Exception {

    public BadRequestException(String errorMsg) {
        super(errorMsg);
    }
}
