package com.ljmu.research.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFoundException ex){
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceDeleted.class)
    public ResponseEntity<SuccessMessage> handleResourceDeleted(ResourceNotFoundException ex){
        SuccessMessage successMessage = new SuccessMessage(HttpStatus.OK.value(), ex.getMessage());
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
}
