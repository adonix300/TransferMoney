package com.example.moneytransfer.service;

import com.example.moneytransfer.exception.ValidationException;
import com.example.moneytransfer.logger.Logger;
import com.example.moneytransfer.model.ErrorResponse;
import com.example.moneytransfer.model.TransferBody;
import com.example.moneytransfer.repository.TransferRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransferService {
    @Autowired
    private TransferRepository transferRepository;
    private Logger logger = Logger.getInstance();
    @Getter
    private String operationId;

    public void addTransfer(TransferBody transferBody) {
        validationBody(transferBody);
        transferRepository.addTransfer(transferBody);
        operationId = String.valueOf(transferRepository.getId());
    }

    public void validationBody(TransferBody transferBody) {
        if (transferBody.getCardFromCVV() == null || transferBody.getCardFromCVV().length() != 3) {
            //TODO logger
            throw new ValidationException("Invalid CVV: " + transferBody.getCardFromCVV());
        } else if (transferBody.getCardFromNumber() == null || transferBody.getCardFromNumber().length() != 16) {
            //TODO logger
            throw new ValidationException("Invalid CardFromNumber: " + transferBody.getCardFromNumber());
        } else if (transferBody.getCardToNumber() == null || transferBody.getCardToNumber().length() != 16) {
            //TODO logger
            throw new ValidationException("Invalid CardToNumber: " + transferBody.getCardToNumber());
        } else if (transferBody.getCardFromValidTill() == null) {
            //TODO logger
            throw new ValidationException("Invalid CardFromValidTill: " + transferBody.getCardFromValidTill());
        } else if (transferBody.getAmount().getValue() == null || transferBody.getAmount().getCurrency() == null) {
            //TODO logger
            throw new ValidationException("Invalid Amount: " + transferBody.getAmount());
        }
    }

}
