package com.example.moneytransfer.service;

import com.example.moneytransfer.api.TransferServiceApi;
import com.example.moneytransfer.records.TransferBody;
import com.example.moneytransfer.repository.TransferRepository;
import com.example.moneytransfer.validators.TransferValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferService implements TransferServiceApi {
    private final TransferRepository transferRepository;
    private final TransferValidator transferValidator;

    @Autowired
    public TransferService(TransferRepository transferRepository, TransferValidator transferValidator) {
        this.transferRepository = transferRepository;
        this.transferValidator = transferValidator;
    }

    public String addTransfer(TransferBody transferBody) {
        transferValidator.validateTransferBody(transferBody);
        return transferRepository.addTransfer(transferBody);
    }

}
