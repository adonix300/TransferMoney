package com.example.moneytransfer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfirmOperationBody {
    @JsonProperty("operationId")
    private String operationId = null;

    @JsonProperty("code")
    private String code = null;
}
