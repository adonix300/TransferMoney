package com.example.moneytransfer.validators;

import com.example.moneytransfer.exception.ValidationException;
import com.example.moneytransfer.logger.Logger;
import com.example.moneytransfer.model.TransferBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferValidator {
    private final Logger logger;
    @Autowired
    public TransferValidator(Logger logger) {
        this.logger = logger;
    }

    public void validateTransferBody(TransferBody transferBody) {
        if (transferBody.getCardFromCVV() == null || transferBody.getCardFromCVV().length() != 3)
            loggerAndValidation("Invalid CVV: " + transferBody.getCardFromCVV());

        if (transferBody.getCardFromNumber() == null || transferBody.getCardFromNumber().length() != 16)
            loggerAndValidation("Invalid CardFromNumber: " + transferBody.getCardFromNumber());

        if (transferBody.getCardToNumber() == null || transferBody.getCardToNumber().length() != 16)
            loggerAndValidation("Invalid CardToNumber: " + transferBody.getCardToNumber());

        if (transferBody.getCardFromValidTill() == null || transferBody.getCardFromValidTill().length() != 5)
            loggerAndValidation("Invalid CardFromValidTill: " + transferBody.getCardFromValidTill());

        if (transferBody.getAmount() == null)
            loggerAndValidation("Invalid Amount");

        if (transferBody.getAmount().getValue() == null)
            loggerAndValidation("Invalid Amount value: null");

        if (transferBody.getAmount().getCurrency() == null)
            loggerAndValidation("Invalid Amount currency");

    }

    private void loggerAndValidation(String msg) {
        logger.log(msg);
        throw new ValidationException(msg);
    }
}
