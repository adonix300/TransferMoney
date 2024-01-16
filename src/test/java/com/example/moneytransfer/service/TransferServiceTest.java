package com.example.moneytransfer.service;

import com.example.moneytransfer.records.TransferAmount;
import com.example.moneytransfer.records.TransferBody;
import com.example.moneytransfer.repository.TransferRepository;
import com.example.moneytransfer.validators.TransferValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class TransferServiceTest {

    @Mock
    TransferRepository transferRepository;
    @Mock
    TransferValidator transferValidator;
    @InjectMocks
    TransferService transferService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void addTransfer_ValidTransferBody_ShouldCallRepository() {
        TransferBody transferBody = new TransferBody(
                "1234567812345678",
                "12/25",
                "234",
                "2345678923456789",
                new TransferAmount(43424, "RUR"));
        transferService.addTransfer(transferBody);
        verify(transferRepository).addTransfer(transferBody);
    }


}