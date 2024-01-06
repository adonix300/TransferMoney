package com.example.moneytransfer.service;

import com.example.moneytransfer.exception.ValidationException;
import com.example.moneytransfer.logger.Logger;
import com.example.moneytransfer.model.ConfirmOperationBody;
import com.example.moneytransfer.model.TransferBody;
import com.example.moneytransfer.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferService {
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private Logger logger;

    public String addTransfer(TransferBody transferBody) {
        validationTransferBody(transferBody);
        return transferRepository.addTransfer(transferBody);
    }

    public String confirmOperation(ConfirmOperationBody body) {
        validationConfirmOperation(body);
        try {
            if (body.getCode().equals("0000")) {
                return transferRepository.confirmOperation(body.getOperationId());
            } else {
                loggerAndValidation("Wrong code: " + body.getCode());
            }
        } catch (ValidationException e) {
            transferRepository.confirmOperationFailed(body.getOperationId());
        }
        return null;
    }

    public void validationTransferBody(TransferBody transferBody) {
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

    public void validationConfirmOperation(ConfirmOperationBody body) {
        if (body.getCode() == null || body.getCode().length() != 4) {
            loggerAndValidation("Invalid Code: " + body.getCode());
        }
        if (Integer.parseInt(body.getOperationId()) < 0) {
            loggerAndValidation("Invalid id: " + body.getOperationId());
        }
    }

    private void loggerAndValidation(String msg) {
        logger.log(msg);
        throw new ValidationException(msg);
    }
}
