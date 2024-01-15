package com.example.moneytransfer.service;

import com.example.moneytransfer.exception.ValidationException;
import com.example.moneytransfer.logger.Logger;
import com.example.moneytransfer.model.TransferAmount;
import com.example.moneytransfer.model.TransferBody;
import com.example.moneytransfer.repository.TransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class TransferServiceTest {

    @Mock
    TransferRepository transferRepository;
    @Mock
    Logger logger;
    @InjectMocks
    TransferService transferService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void addTransfer_ValidTransferBody_ShouldCallRepository() {
        TransferBody transferBody = new TransferBody("1234567812345678", "12/25", "234", "2345678923456789", new TransferAmount(43424, "RUR"));
        transferService.addTransfer(transferBody);
        verify(transferRepository).addTransfer(transferBody);
    }


}