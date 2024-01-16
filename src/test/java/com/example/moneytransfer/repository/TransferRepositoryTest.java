package com.example.moneytransfer.repository;

import com.example.moneytransfer.model.TransferStatus;
import com.example.moneytransfer.records.TransferAmount;
import com.example.moneytransfer.records.TransferBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.MockitoAnnotations.initMocks;

class TransferRepositoryTest {
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
                new TransferAmount(12345, "RUR"));
        int sizeBefore = TransferRepository.getTransferMap().size();

        String id = transferRepository.addTransfer(transferBody);
        assertNotNull(id);

        int sizeAfter = TransferRepository.getTransferMap().size();
        assertEquals(sizeBefore + 1, sizeAfter);
    }

    @Test
    void confirmOperation() {
        TransferBody transferBody = new TransferBody(
                "1111222233334444",
                "12/25",
                "123",
                "55556666777788889999",
                new TransferAmount(12345, "RUR"));
        String id = transferRepository.addTransfer(transferBody);
        transferRepository.confirmOperation(id);
        assertEquals(TransferStatus.SUCCESSFUL, TransferRepository.getTransferMap().get(id).getStatus());
    }

    @Test
    void confirmOperationFailed() {
        TransferBody transferBody = new TransferBody(
                "1111222233334444",
                "12/25",
                "123",
                "55556666777788889999",
                new TransferAmount(12345, "RUR"));
        String id = transferRepository.addTransfer(transferBody);
        transferRepository.confirmOperationFailed(id);
        assertEquals(TransferStatus.FAILED, TransferRepository.getTransferMap().get(id).getStatus());
    }
}