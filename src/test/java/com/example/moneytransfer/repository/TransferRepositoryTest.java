package com.example.moneytransfer.repository;

import com.example.moneytransfer.logger.Logger;
import com.example.moneytransfer.model.TransferBody;
import com.example.moneytransfer.model.TransferStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class TransferRepositoryTest {
    @Mock
    Logger logger;
    @InjectMocks
    TransferRepository transferRepository;

    @BeforeEach
    void setUp() {
        initMocks(this);
        TransferRepository.getTransferMap().clear();
    }

    @Test
    void addTransfer_shouldAddTransferSuccessfully() {
        TransferBody transferBody = new TransferBody();
        int sizeBefore = TransferRepository.getTransferMap().size();

        String id = transferRepository.addTransfer(transferBody);
        assertNotNull(id);

        int sizeAfter = TransferRepository.getTransferMap().size();
        assertEquals(sizeBefore + 1, sizeAfter);

        verify(logger).log("ID " + id + ": " +
                "cardFrom: Number: " + transferBody.getCardFromNumber() + ", " +
                "ValidTill: " + transferBody.getCardFromValidTill() + ", " +
                "CVV: " + transferBody.getCardFromCVV() + ". " +
                "cardTo: Number: " + transferBody.getCardToNumber() + ". " +
                "Amount: " + transferBody.getAmount() + ". " +
                "Status: " + TransferStatus.PROCESSING + ".");
    }

    @Test
    void confirmOperation() {
        TransferBody transferBody = new TransferBody();
        String id = transferRepository.addTransfer(transferBody);
        transferRepository.confirmOperation(id);
        assertEquals(TransferStatus.SUCCESSFUL, TransferRepository.getTransferMap().get(id).getStatus());

        verify(logger).log("ID " + id + ": " + "New status: " + TransferStatus.SUCCESSFUL);
    }

    @Test
    void confirmOperationFailed() {
        TransferBody transferBody = new TransferBody();
        String id = transferRepository.addTransfer(transferBody);
        transferRepository.confirmOperationFailed(id);
        assertEquals(TransferStatus.FAILED, TransferRepository.getTransferMap().get(id).getStatus());

        verify(logger).log("ID " + id + ": " + "New status: " + TransferStatus.FAILED);
    }
}