package com.example.moneytransfer.controller;

import com.example.moneytransfer.api.TransferControllerApi;
import com.example.moneytransfer.exception.ValidationException;
import com.example.moneytransfer.model.ConfirmOperationBody;
import com.example.moneytransfer.model.ConfirmOperationResponse;
import com.example.moneytransfer.model.TransferBody;
import com.example.moneytransfer.service.ConfirmOperationService;
import com.example.moneytransfer.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController implements TransferControllerApi {
    private final TransferService transferService;
    private final ConfirmOperationService confirmOperationService;

    @Autowired
    public TransferController(TransferService transferService, ConfirmOperationService confirmOperationService) {
        this.transferService = transferService;
        this.confirmOperationService = confirmOperationService;
    }

    @Override
    public ResponseEntity<ConfirmOperationResponse> transferPost(TransferBody transferBody) {
        try {
            String id = transferService.addTransfer(transferBody);
            return ResponseEntity
                    .ok(ConfirmOperationResponse
                            .builder()
                            .operationId(id)
                            .build());
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public ResponseEntity<ConfirmOperationResponse> confirmOperationPost(ConfirmOperationBody body) {
        try {
            String id = confirmOperationService.confirmOperation(body);
            return ResponseEntity
                    .ok(ConfirmOperationResponse.builder().operationId(id).build());
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }
}