package com.example.moneytransfer.api;

import com.example.moneytransfer.records.TransferBody;

public interface TransferRepositoryApi {
    String addTransfer(TransferBody transferBody);
    String confirmOperation(String id);
    String confirmOperationFailed(String id);

}
