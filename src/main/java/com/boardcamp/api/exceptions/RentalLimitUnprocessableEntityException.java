package com.boardcamp.api.exceptions;

public class RentalLimitUnprocessableEntityException extends RuntimeException {
    public RentalLimitUnprocessableEntityException(String message) {
        super(message);
    }
}
