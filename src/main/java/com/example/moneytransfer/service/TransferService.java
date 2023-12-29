package com.example.moneytransfer.service;

import com.example.moneytransfer.exception.ValidationException;
import com.example.moneytransfer.logger.Logger;
import com.example.moneytransfer.model.TransferBody;
import com.example.moneytransfer.repository.TransferRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferService {
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private Logger logger;
    @Getter
    private String operationId;

    public void addTransfer(TransferBody transferBody) {
        validationBody(transferBody);
        transferRepository.addTransfer(transferBody);
        operationId = String.valueOf(transferRepository.getId());
    }

    public void validationBody(TransferBody transferBody) {
        if (transferBody.getCardFromCVV() == null || transferBody.getCardFromCVV().length() != 3) {
            loggerAndValidation("Invalid CVV: " + transferBody.getCardFromCVV());
        } else if (transferBody.getCardFromNumber() == null || transferBody.getCardFromNumber().length() != 16) {
            loggerAndValidation("Invalid CardFromNumber: " + transferBody.getCardFromNumber());
        } else if (transferBody.getCardToNumber() == null || transferBody.getCardToNumber().length() != 16) {
            loggerAndValidation("Invalid CardToNumber: " + transferBody.getCardToNumber());
        } else if (transferBody.getCardFromValidTill() == null) {
            loggerAndValidation("Invalid CardFromValidTill: " + transferBody.getCardFromValidTill());
        } else if (transferBody.getAmount().getValue() == null || transferBody.getAmount().getCurrency() == null) {
            loggerAndValidation("Invalid Amount: " + transferBody.getAmount());
        }
    }

    private void loggerAndValidation(String msg) {
        logger.log(msg);
        throw new ValidationException(msg);
    }

}
