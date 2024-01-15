package com.example.moneytransfer.api;

import com.example.moneytransfer.model.ConfirmOperationBody;
import com.example.moneytransfer.model.TransferBody;

public interface TransferServiceApi {
    String addTransfer(TransferBody transferBody);
}
