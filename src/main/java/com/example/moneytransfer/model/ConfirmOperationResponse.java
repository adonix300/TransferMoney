package com.example.moneytransfer.model;

import com.example.moneytransfer.repository.TransferRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfirmOperationResponse {
    @JsonProperty("operationId")
    private String operationId = null;
}
