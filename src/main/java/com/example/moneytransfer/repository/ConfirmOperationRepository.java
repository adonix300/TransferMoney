package com.example.moneytransfer.repository;

import com.example.moneytransfer.model.ConfirmOperationBody;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Data
public class ConfirmOperationRepository {
    private static final ConcurrentHashMap<String, ConfirmOperationBody> confirmOperationMap = new ConcurrentHashMap<>();
    private AtomicInteger id = new AtomicInteger(0);

    public void addConfirmOperation(ConfirmOperationBody confirmOperationBody) {
        String currentId = id.incrementAndGet() + "";
        confirmOperationMap.put(currentId, confirmOperationBody);
    }

}
