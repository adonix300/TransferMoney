package com.example.moneytransfer.validators;

import com.example.moneytransfer.exception.ValidationException;
import com.example.moneytransfer.logger.Logger;
import com.example.moneytransfer.records.ConfirmOperationBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmOperationValidator {
    private final Logger logger;
    @Autowired
    public ConfirmOperationValidator(Logger logger) {
        this.logger = logger;
    }

    public void validate(ConfirmOperationBody body) {
        if (body.code() == null || body.code().length() != 4) {
            loggerAndValidation("Invalid Code: " + body.code());
        }
        if (body.operationId() == null || !isNumeric(body.operationId()) || Integer.parseInt(body.operationId()) < 0) {
            loggerAndValidation("Invalid id: " + body.operationId());
        }
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private void loggerAndValidation(String msg) {
        logger.log(msg);
        throw new ValidationException(msg);
    }
}
