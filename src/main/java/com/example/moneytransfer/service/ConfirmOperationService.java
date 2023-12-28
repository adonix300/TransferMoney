package com.example.moneytransfer.service;

import com.example.moneytransfer.exception.ValidationException;
import com.example.moneytransfer.model.ConfirmOperationBody;
import com.example.moneytransfer.model.ConfirmOperationResponse;
import com.example.moneytransfer.repository.ConfirmOperationRepository;
import com.example.moneytransfer.repository.TransferRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ConfirmOperationService {
    @Autowired
    ConfirmOperationRepository confirmOperationRepository;
    @Getter
    String operationId;

    public void confirmOperation(ConfirmOperationBody body) {
        validationConfirmOperation(body);
        confirmOperationRepository.addConfirmOperation(body);
        operationId = String.valueOf(confirmOperationRepository.getId());
    }

    public void validationConfirmOperation(ConfirmOperationBody body) {
        if (body.getCode() == null || body.getCode().length() != 4) {
            //TODO logger
            throw new ValidationException("Invalid Code");
        }
        if (!body.getCode().equals("0000")) {
            //TODO logger
            throw new ValidationException("Wrong Code");
        }
    }

}
