package com.example.moneytransfer.handlers;

import com.example.moneytransfer.exception.ValidationException;
import com.example.moneytransfer.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransferExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException e) {
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse
                        .builder()
                        .message("Ошибка валидации данных")
                        .errorType("ValidationError")
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity
                .internalServerError()
                .body(ErrorResponse
                        .builder()
                        .message("Ошибка во время трансфера")
                        .errorType("TransferError")
                        .build());
    }
}
