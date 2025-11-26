package com.pm.patient_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String , String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = error instanceof FieldError ? ((FieldError) error).getField() : error.getObjectName();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(
                errors,
                HttpStatus.BAD_REQUEST
        );
    }


    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String , String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        Map<String , String> error = new HashMap<>();
        error.put("email", ex.getMessage());
        return new ResponseEntity<>(error , HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String , String>> handlePatientNotFoundException(PatientNotFoundException ex) {
        Map<String , String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        error.put("error code:" , ex.getErrorCode());
        return new ResponseEntity<>(error , ex.getStatus());
    }
}
