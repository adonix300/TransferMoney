package com.example.moneytransfer.repository;

import com.example.moneytransfer.api.TransferRepositoryApi;
import com.example.moneytransfer.exception.ValidationException;
import com.example.moneytransfer.model.Transfer;
import com.example.moneytransfer.model.TransferStatus;
import com.example.moneytransfer.records.TransferBody;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TransferRepository implements TransferRepositoryApi {
    private static final Logger logger = LogManager.getLogger(TransferRepository.class);
    @Getter
    private static final ConcurrentMap<String, Transfer> transferMap = new ConcurrentHashMap<>();
    private AtomicInteger id = new AtomicInteger(0);

    public String addTransfer(TransferBody transferBody) {
        String currentId = id.incrementAndGet() + "";
        Transfer transfer = new Transfer(transferBody, TransferStatus.PROCESSING);
        transferMap.put(currentId, transfer);

        logger.info("ID " + id + ": " +
                "cardFrom: Number: " + transferBody.cardFromNumber() + ", " +
                "ValidTill: " + transferBody.cardFromValidTill() + ", " +
                "CVV: " + transferBody.cardFromCVV() + ". " +
                "cardTo: Number: " + transferBody.cardToNumber() + ". " +
                "Amount: " + transferBody.amount() + ". " +
                "Status: " + TransferStatus.PROCESSING + ".");
        return currentId;
    }

    public String confirmOperation(String id) {
        Transfer transfer = transferMap.get(id);
        if (transfer != null) {
            transfer.setStatus(TransferStatus.SUCCESSFUL);
            logger.info("ID " + id + ": " +
                    "New status: " + TransferStatus.SUCCESSFUL);
            return id;
        } else {
            throw new ValidationException("Transfer is null");
        }
    }

    public String confirmOperationFailed(String id) {
        Transfer transfer = transferMap.get(id);
        if (transfer != null) {
            transfer.setStatus(TransferStatus.FAILED);
            logger.info("ID " + id + ": " +
                    "New status: " + TransferStatus.FAILED);
            return null;
        } else {
            throw new ValidationException("Transfer is null");
        }
    }
}
