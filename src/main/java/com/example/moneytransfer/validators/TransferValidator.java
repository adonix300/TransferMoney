package com.example.moneytransfer.validators;

import com.example.moneytransfer.exception.ValidationException;
import com.example.moneytransfer.records.TransferBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class TransferValidator {
    private static final Logger logger = LogManager.getLogger(TransferValidator.class);

    public void validateTransferBody(TransferBody transferBody) {
        if (transferBody.cardFromCVV() == null || transferBody.cardFromCVV().length() != 3)
            logAndThrowValidationException("Invalid CVV: " + transferBody.cardFromCVV());

        if (transferBody.cardFromNumber() == null || transferBody.cardFromNumber().length() != 16)
            logAndThrowValidationException("Invalid CardFromNumber: " + transferBody.cardFromNumber());

        if (transferBody.cardToNumber() == null || transferBody.cardToNumber().length() != 16)
            logAndThrowValidationException("Invalid CardToNumber: " + transferBody.cardToNumber());

        if (transferBody.cardFromValidTill() == null || transferBody.cardFromValidTill().length() != 5)
            logAndThrowValidationException("Invalid CardFromValidTill: " + transferBody.cardFromValidTill());

        if (transferBody.amount() == null)
            logAndThrowValidationException("Invalid Amount");

        if (transferBody.amount().value() == null)
            logAndThrowValidationException("Invalid Amount value: null");

        if (transferBody.amount().currency() == null)
            logAndThrowValidationException("Invalid Amount currency");

    }

    private void logAndThrowValidationException(String msg) {
        logger.warn(msg);
        throw new ValidationException(msg);
    }
}
