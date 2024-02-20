package com.boardcamp.api.exceptions;

public class RentalReturnedUnprocessableEntityException extends RuntimeException {
    public RentalReturnedUnprocessableEntityException(String message) {
        super(message);
    }
}
