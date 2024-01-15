package com.example.moneytransfer.validators;

import com.example.moneytransfer.exception.ValidationException;
import com.example.moneytransfer.logger.Logger;
import com.example.moneytransfer.model.ConfirmOperationBody;
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
        if (body.getCode() == null || body.getCode().length() != 4) {
            loggerAndValidation("Invalid Code: " + body.getCode());
        }
        if (body.getOperationId() == null || !isNumeric(body.getOperationId()) || Integer.parseInt(body.getOperationId()) < 0) {
            loggerAndValidation("Invalid id: " + body.getOperationId());
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
