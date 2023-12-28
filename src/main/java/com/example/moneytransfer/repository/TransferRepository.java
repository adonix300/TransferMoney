package com.example.moneytransfer.repository;

import com.example.moneytransfer.model.TransferBody;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Data
public class TransferRepository {
    private static final ConcurrentHashMap<String, TransferBody> transferMap = new ConcurrentHashMap<>();
    private AtomicInteger id = new AtomicInteger(0);

    public void addTransfer(TransferBody transferBody) {
        String currentId = id.incrementAndGet() + "";
        transferMap.put(currentId, transferBody);
    }

    public void printMap() {
        System.out.println(transferMap.size());
    }
}
