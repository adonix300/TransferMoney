package com.example.moneytransfer.api;

import com.example.moneytransfer.model.ConfirmOperationBody;

public interface ConfirmOperationServiceApi {
    String confirmOperation(ConfirmOperationBody body);
}
