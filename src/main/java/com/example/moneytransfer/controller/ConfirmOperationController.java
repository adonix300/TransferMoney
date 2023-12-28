package com.example.moneytransfer.controller;

import com.example.moneytransfer.api.ConfirmOperationApi;
import com.example.moneytransfer.exception.ValidationException;
import com.example.moneytransfer.model.ConfirmOperationBody;
import com.example.moneytransfer.model.ConfirmOperationResponse;
import com.example.moneytransfer.model.ErrorResponse;
import com.example.moneytransfer.service.ConfirmOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfirmOperationController implements ConfirmOperationApi {
    @Autowired
    ConfirmOperationService confirmOperationService;

    @Override
    public ResponseEntity<?> confirmOperationPost(ConfirmOperationBody body) {
        try {
            confirmOperationService.confirmOperation(body);
            return ResponseEntity
                    .ok(ConfirmOperationResponse.builder().operationId(confirmOperationService.getOperationId()).build());
        } catch (ValidationException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorResponse
                            .builder()
                            .message("Ошибка валидации данных")
                            .errorType("ValidationError")
                            .build());
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(ErrorResponse
                            .builder()
                            .message("Ошибка во время трансфера")
                            .errorType("TransferError")
                            .build());
        }
    }
}
