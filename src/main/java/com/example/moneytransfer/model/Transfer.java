package com.example.moneytransfer.model;

import com.example.moneytransfer.records.TransferBody;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    private TransferBody body;
    private TransferStatus status;
}
