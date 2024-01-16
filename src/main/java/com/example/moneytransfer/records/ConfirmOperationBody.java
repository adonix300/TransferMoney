package com.example.moneytransfer.records;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
public record ConfirmOperationBody(
        @JsonProperty("operationId") String operationId,
        @JsonProperty("code") String code
) {
}
