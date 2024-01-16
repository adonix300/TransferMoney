package com.example.moneytransfer.validators;

import com.example.moneytransfer.exception.ValidationException;
import com.example.moneytransfer.logger.Logger;
import com.example.moneytransfer.records.TransferBody;
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
        if (transferBody.cardFromCVV() == null || transferBody.cardFromCVV().length() != 3)
            loggerAndValidation("Invalid CVV: " + transferBody.cardFromCVV());

        if (transferBody.cardFromNumber() == null || transferBody.cardFromNumber().length() != 16)
            loggerAndValidation("Invalid CardFromNumber: " + transferBody.cardFromNumber());

        if (transferBody.cardToNumber() == null || transferBody.cardToNumber().length() != 16)
            loggerAndValidation("Invalid CardToNumber: " + transferBody.cardToNumber());

        if (transferBody.cardFromValidTill() == null || transferBody.cardFromValidTill().length() != 5)
            loggerAndValidation("Invalid CardFromValidTill: " + transferBody.cardFromValidTill());

        if (transferBody.amount() == null)
            loggerAndValidation("Invalid Amount");

        if (transferBody.amount().value() == null)
            loggerAndValidation("Invalid Amount value: null");

        if (transferBody.amount().currency() == null)
            loggerAndValidation("Invalid Amount currency");

    }

    private void loggerAndValidation(String msg) {
        logger.log(msg);
        throw new ValidationException(msg);
    }
}
