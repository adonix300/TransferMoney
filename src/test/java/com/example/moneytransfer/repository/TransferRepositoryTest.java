package com.example.moneytransfer.repository;

import com.example.moneytransfer.logger.Logger;
import com.example.moneytransfer.records.TransferAmount;
import com.example.moneytransfer.records.TransferBody;
import com.example.moneytransfer.model.TransferStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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
        TransferBody transferBody = new TransferBody(
                "1111222233334444",
                "12/25",
                "123",
                "55556666777788889999",
                new TransferAmount(12345,"RUR"));
        int sizeBefore = TransferRepository.getTransferMap().size();

        String id = transferRepository.addTransfer(transferBody);
        assertNotNull(id);

        int sizeAfter = TransferRepository.getTransferMap().size();
        assertEquals(sizeBefore + 1, sizeAfter);

        verify(logger).log("ID " + id + ": " +
                "cardFrom: Number: " + transferBody.cardFromNumber() + ", " +
                "ValidTill: " + transferBody.cardFromValidTill() + ", " +
                "CVV: " + transferBody.cardFromCVV() + ". " +
                "cardTo: Number: " + transferBody.cardToNumber() + ". " +
                "Amount: " + transferBody.amount() + ". " +
                "Status: " + TransferStatus.PROCESSING + ".");
    }

    @Test
    void confirmOperation() {
        TransferBody transferBody = new TransferBody(
                "1111222233334444",
                "12/25",
                "123",
                "55556666777788889999",
                new TransferAmount(12345,"RUR"));
        String id = transferRepository.addTransfer(transferBody);
        transferRepository.confirmOperation(id);
        assertEquals(TransferStatus.SUCCESSFUL, TransferRepository.getTransferMap().get(id).getStatus());

        verify(logger).log("ID " + id + ": " + "New status: " + TransferStatus.SUCCESSFUL);
    }

    @Test
    void confirmOperationFailed() {
        TransferBody transferBody = new TransferBody(
                "1111222233334444",
                "12/25",
                "123",
                "55556666777788889999",
                new TransferAmount(12345,"RUR"));
        String id = transferRepository.addTransfer(transferBody);
        transferRepository.confirmOperationFailed(id);
        assertEquals(TransferStatus.FAILED, TransferRepository.getTransferMap().get(id).getStatus());

        verify(logger).log("ID " + id + ": " + "New status: " + TransferStatus.FAILED);
    }
}