package com.example.moneytransfer.repository;

import com.example.moneytransfer.api.TransferRepositoryApi;
import com.example.moneytransfer.exception.ValidationException;
import com.example.moneytransfer.logger.Logger;
import com.example.moneytransfer.model.Transfer;
import com.example.moneytransfer.model.TransferBody;
import com.example.moneytransfer.model.TransferStatus;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TransferRepository implements TransferRepositoryApi {
    private final Logger logger;

    @Autowired
    public TransferRepository(Logger logger) {
        this.logger = logger;
    }

    @Getter
    private static final ConcurrentMap<String, Transfer> transferMap = new ConcurrentHashMap<>();
    private AtomicInteger id = new AtomicInteger(0);

    public String addTransfer(TransferBody transferBody) {
        String currentId = id.incrementAndGet() + "";
        Transfer transfer = new Transfer(transferBody, TransferStatus.PROCESSING);
        transferMap.put(currentId, transfer);

        logger.log("ID " + currentId + ": " +
                "cardFrom: Number: " + transferBody.getCardFromNumber() + ", " +
                "ValidTill: " + transferBody.getCardFromValidTill() + ", " +
                "CVV: " + transferBody.getCardFromCVV() + ". " +
                "cardTo: Number: " + transferBody.getCardToNumber() + ". " +
                "Amount: " + transferBody.getAmount() + ". " +
                "Status: " + TransferStatus.PROCESSING + ".");
        return currentId;
    }

    public String confirmOperation(String id) {
        Transfer transfer = transferMap.get(id);
        if (transfer != null) {
            transfer.setStatus(TransferStatus.SUCCESSFUL);
            logger.log("ID " + id + ": " +
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
            logger.log("ID " + id + ": " +
                    "New status: " + TransferStatus.FAILED);
            return null;
        } else {
            throw new ValidationException("Transfer is null");
        }
    }
}
