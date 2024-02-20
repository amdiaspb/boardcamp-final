package com.boardcamp.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    /* CUSTOMER ================================================================================ */
    
    @ExceptionHandler({ CustomerCpfConflictException.class })
    public ResponseEntity<Object> handleCustomerCpfConflict(CustomerCpfConflictException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler({ CustomerNotFoundException.class })
    public ResponseEntity<Object> handleCustomerNotFound(CustomerNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    /* GAMES =================================================================================== */

    @ExceptionHandler({ GameNameConflictException.class })
    public ResponseEntity<Object> handleGameNameConflict(GameNameConflictException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler({ GameNotFoundException.class })
    public ResponseEntity<Object> handleGameNotFound(GameNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

}
