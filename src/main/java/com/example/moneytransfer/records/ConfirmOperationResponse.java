package com.example.moneytransfer.records;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;


@Builder
public record ConfirmOperationResponse(
        @JsonProperty("operationId") String operationId
) {}
