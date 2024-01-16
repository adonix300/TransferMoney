package com.example.moneytransfer.api;

import com.example.moneytransfer.records.ConfirmOperationBody;

public interface ConfirmOperationServiceApi {
    String confirmOperation(ConfirmOperationBody body);
}
