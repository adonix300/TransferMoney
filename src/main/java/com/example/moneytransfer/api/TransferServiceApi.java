package com.example.moneytransfer.api;

import com.example.moneytransfer.records.TransferBody;

public interface TransferServiceApi {
    String addTransfer(TransferBody transferBody);
}
