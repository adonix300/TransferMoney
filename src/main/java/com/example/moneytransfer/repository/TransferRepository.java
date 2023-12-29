package com.example.moneytransfer.repository;

import com.example.moneytransfer.model.ConfirmOperationBody;
import com.example.moneytransfer.model.TransferBody;
import com.example.moneytransfer.model.TransferStatus;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TransferRepository {
    private static final ConcurrentMap<String, TransferBody> transferMap = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, TransferStatus> transfersStatusList = new ConcurrentHashMap<>();
    private AtomicInteger id = new AtomicInteger(0);

    public String addTransfer(TransferBody transferBody) {
        String currentId = id.incrementAndGet() + "";
        transferMap.put(currentId, transferBody);
        transfersStatusList.put(currentId, TransferStatus.LOADING);
        return currentId;
    }

    public String confirmOperation(String id) {
        transfersStatusList.put(id, TransferStatus.OKAY);
        return id;
    }

    public void confirmOperationFailed(String id) {
        transfersStatusList.put(id, TransferStatus.FAILED);
    }
}
