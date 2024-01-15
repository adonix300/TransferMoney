package com.example.moneytransfer.service;

import com.example.moneytransfer.api.ConfirmOperationServiceApi;
import com.example.moneytransfer.exception.ValidationException;
import com.example.moneytransfer.model.ConfirmOperationBody;
import com.example.moneytransfer.repository.TransferRepository;
import com.example.moneytransfer.validators.ConfirmOperationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmOperationService implements ConfirmOperationServiceApi {
    private final String CODE = "0000";
    private final TransferRepository transferRepository;
    private final ConfirmOperationValidator confirmOperationValidator;

    @Autowired
    public ConfirmOperationService(TransferRepository transferRepository, ConfirmOperationValidator confirmOperationValidator) {
        this.transferRepository = transferRepository;
        this.confirmOperationValidator = confirmOperationValidator;
    }

    @Override
    public String confirmOperation(ConfirmOperationBody body) {
        confirmOperationValidator.validate(body);
        if (CODE.equals(body.getCode())) {
            return transferRepository.confirmOperation(body.getOperationId());
        } else {
            return transferRepository.confirmOperationFailed(body.getOperationId());
        }
    }
}
