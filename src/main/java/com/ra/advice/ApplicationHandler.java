package com.ra.advice;

import com.ra.exception.NotFoundException;
import com.ra.validate.ResponseValidate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class ApplicationHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleInvalidException(MethodArgumentNotValidException exception){
        Map<String,String> errors = new HashMap<>();
        exception.getFieldErrors().forEach(error->{
            errors.put(error.getField(),error.getDefaultMessage());
        });
        return new ResponseEntity<>
                (new ResponseValidate<>(400,"BAD REQUEST",errors), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException exception){
        return new ResponseEntity<>(new ResponseValidate<>(404,"Not Found",exception.getMessage()),HttpStatus.NOT_FOUND);
    }
}
