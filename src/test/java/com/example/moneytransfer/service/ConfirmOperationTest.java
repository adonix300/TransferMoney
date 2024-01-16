package com.example.moneytransfer.service;

import com.example.moneytransfer.logger.Logger;
import com.example.moneytransfer.records.ConfirmOperationBody;
import com.example.moneytransfer.repository.TransferRepository;
import com.example.moneytransfer.validators.ConfirmOperationValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ConfirmOperationTest {
    @Mock
    private ConfirmOperationValidator confirmOperationValidator;
    @Mock
    private TransferRepository transferRepository;
    @Mock
    private Logger logger;
    @InjectMocks
    private ConfirmOperationService confirmOperationService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    public void testConfirmOperation_ShouldCallSuccess() {
        ConfirmOperationBody body = new ConfirmOperationBody("1", "0000");

        when(transferRepository.confirmOperation(anyString())).thenReturn("1");

        confirmOperationService.confirmOperation(body);

        verify(confirmOperationValidator, times(1)).validate(body);
        verify(transferRepository, times(1)).confirmOperation(body.operationId());
    }

    @Test
    public void testConfirmOperation_ShouldCallFailed() {
        ConfirmOperationBody body = new ConfirmOperationBody("1", "1234");

        confirmOperationService.confirmOperation(body);

        verify(confirmOperationValidator, times(1)).validate(body);
        verify(transferRepository, times(1)).confirmOperationFailed(body.operationId());
    }
}
