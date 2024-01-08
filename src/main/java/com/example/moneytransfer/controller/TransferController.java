package com.example.moneytransfer.controller;

import com.example.moneytransfer.api.TransferApi;
import com.example.moneytransfer.exception.ValidationException;
import com.example.moneytransfer.model.ConfirmOperationBody;
import com.example.moneytransfer.model.ConfirmOperationResponse;
import com.example.moneytransfer.model.ErrorResponse;
import com.example.moneytransfer.model.TransferBody;
import com.example.moneytransfer.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TransferController implements TransferApi {
    @Autowired
    private TransferService transferService;

    @Override
    public ResponseEntity<?> transferPost(TransferBody transferBody) {
        try {
            String id = transferService.addTransfer(transferBody);
            return ResponseEntity
                    .ok(ConfirmOperationResponse
                            .builder()
                            .operationId(id)
                            .build());
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

    @Override
    public ResponseEntity<?> confirmOperationPost(ConfirmOperationBody body) {
        try {
            String id = transferService.confirmOperation(body);
            return ResponseEntity
                    .ok(ConfirmOperationResponse.builder().operationId(id).build());
        } catch (ValidationException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorResponse
                            .builder()
                            .message("Неверный код проверки")
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